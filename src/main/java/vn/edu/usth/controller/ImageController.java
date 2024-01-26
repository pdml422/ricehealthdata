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
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import vn.edu.usth.dto.MapDto;
import vn.edu.usth.exception.DataNotFoundException;
import vn.edu.usth.model.MapPoint;
import vn.edu.usth.payload.ImageRGBFromHyper;
import vn.edu.usth.payload.MapResquest;
import vn.edu.usth.service.image.FileUploadService;
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
    FileUploadService fileUploadService;

    @Inject
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/hyper/{id}")
    public Response getHyperFromUserId(@PathParam("id") int id) {
        return Response.ok(imageService.getImageFromUserId(id)).build();
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("/hyper/{id}/header")
    public Response getHyperHeaderFromUserId(@PathParam("id") int id) {
        return Response.ok(imageService.getHeaderFromUserId(id)).build();
    }

    @DELETE
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/hyper/{id}")
    public Response deleteHyper(@PathParam("id") int imageId) {
        return imageService.deleteImage(imageId);
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

        String filePath = "src/main/resources/Image/" + userId + "/Output/" +
                "hyper_" + request.id + "_" + request.red + "_" + request.green + "_" + request.blue + ".png";

        if (Files.exists(Paths.get(filePath))) {
            return Response.ok("http://100.96.184.148:8888/src/main/resources/Image/" + userId + "/Output/" +
                    "hyper_" + request.id + "_" + request.red + "_" + request.green + "_" + request.blue + ".png").build();
        }
        return Response.ok(imageService.getImageRGBFromHyper(request, userId)).build();
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
    @RolesAllowed({"USER", "ADMIN"})
    @Path("map/{id}")
    public Response deleteData(@PathParam("id") int id) throws DataNotFoundException {
        imageService.deleteData(id);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/hyper")
    @RolesAllowed({"USER", "ADMIN"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response fileUpload(@MultipartForm MultipartFormDataInput input, @HeaderParam("userId") int userId) {
        return Response.ok().entity(fileUploadService.uploadFile(input, userId)).build();
    }

    @GET
    @Path("/resolution/{id}")
    @RolesAllowed({"USER"})
    public Response getResolution(@PathParam("id") int id) {
        return Response.ok(imageService.getResolution(id)).build();
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