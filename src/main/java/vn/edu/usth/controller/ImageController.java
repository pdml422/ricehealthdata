package vn.edu.usth.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import vn.edu.usth.dto.MapDto;
import vn.edu.usth.exception.DataNotFoundException;
import vn.edu.usth.model.MapPoint;
import vn.edu.usth.payload.ImageRGBFromHyper;
import vn.edu.usth.payload.MapResquest;
import vn.edu.usth.service.image.ImageService;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestScoped
@Path("/image")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class ImageController {
    private final ImageService imageService;

    @Inject
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @POST
    @RolesAllowed({"USER"})
    @Path("/rgb")
    public Response getImageRGBFromHyper(@RequestBody ImageRGBFromHyper request, @HeaderParam("userId") int userId) throws IOException {
        if (userId != imageService.getImageFromId(request.id).getUserId()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if (!isPortInUse(8888)) {
            Process p = Runtime.getRuntime().exec("python -m http.server 8888");
        } else {
            System.out.println("The process is already running.");
        }

        String filePath = "src/main/resources/Image/Output/" +
                "hyper_" + request.id + "_" + request.red + "_" + request.green + "_" + request.blue + ".png";

        if (Files.exists(Paths.get(filePath))) {
            return Response.ok("http://100.96.184.148:8888/src/main/resources/Image/Output/" +
                    "hyper_" + request.id + "_" + request.red + "_" + request.green + "_" + request.blue + ".png").build();
        }
        return Response.ok(imageService.getImageRGBFromHyper(request)).build();
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("/map/{id}")
    public Response getMapPoint(@PathParam("id") int id) {
        return Response.ok(imageService.getMapPointFromImageId(id)).build();
    }

    @RolesAllowed({"USER"})
    @POST
    @Path("/map/add")
    public MapPoint addData(@RequestBody MapResquest request) {
        MapPoint newMap = new MapPoint();
        newMap.setX(request.X);
        newMap.setY(request.Y);
        newMap.setImageId(request.imageId);
        newMap.setDataId(request.dataId);

        return imageService.addData(newMap);
    }

    @PUT
    @RolesAllowed({"USER"})
    @Path("map/{id}")
    public MapPoint updateData(@PathParam("id") int id, @Valid MapDto mapDto) throws DataNotFoundException {
        if (imageService != null && mapDto != null) {
            return imageService.updateData(id, mapDto.toMap());
        } else {
            throw new IllegalArgumentException("Not on database");
        }
    }

    @DELETE
    @RolesAllowed({"USER"})
    @Path("map/{id}")
    public Response deleteData(@PathParam("id") int id) throws DataNotFoundException {
        imageService.deleteData(id);
        return Response.status(Response.Status.OK).build();
    }

    public boolean isPortInUse(int port) {
        boolean inUse = false;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            inUse = false;
        } catch (IOException e) {
            inUse = true;
        }

        return inUse;
    }
}