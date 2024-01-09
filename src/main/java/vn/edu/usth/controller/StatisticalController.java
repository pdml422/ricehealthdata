package vn.edu.usth.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import vn.edu.usth.dto.StatisticalDto;
import vn.edu.usth.exception.DataNotFoundException;
import vn.edu.usth.exception.ExceptionDataHandler;
import vn.edu.usth.model.Statistical;
import vn.edu.usth.payload.SearchStatistical;
import vn.edu.usth.payload.StatisticalRequest;
import vn.edu.usth.service.data.StatisticalService;

import java.util.List;

@RequestScoped
@Path("/statistical")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class StatisticalController {
    private final StatisticalService statisticalService;

    @Inject
    public StatisticalController(StatisticalService statisticalService) {
        this.statisticalService = statisticalService;
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("/search")
    @Operation(summary = "Gets statistic data", description = "Retrieves statistic data")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Statistical.class))),
            @APIResponse(responseCode = "404", description = "Statistic data not found !",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionDataHandler.class)))
    })
    public List<Statistical> search(@RequestBody SearchStatistical searchStatistical, @HeaderParam("userId") int userId) throws DataNotFoundException {
        if (statisticalService.search(searchStatistical, userId).isEmpty()) {
            throw new DataNotFoundException("Data not found");
        }
        return statisticalService.search(searchStatistical, userId);
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("/searchAll")
    @Operation(summary = "Gets all statistic data", description = "Retrieves all statistic data")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Statistical.class))),
            @APIResponse(responseCode = "404", description = "Statistic data not found !",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionDataHandler.class)))
    })
    public List<Statistical> searchAll(@HeaderParam("userId") int userId) throws DataNotFoundException {
        if (statisticalService.searchAll(userId).isEmpty()) {
            throw new DataNotFoundException("Data not found");
        }
        return statisticalService.searchAll(userId);
    }

    @RolesAllowed({"USER"})
    @POST
    @Path("/create") @Produces(MediaType.APPLICATION_JSON)
    public Statistical addData(@RequestBody StatisticalRequest request, @HeaderParam("userId") int userId) throws Exception {
        SearchStatistical search = new SearchStatistical();
        search.setReplicate(request.replicate);
        search.setSubReplicate(request.subReplicate);
        search.setDate(request.date);

        if (!statisticalService.search(search, userId).isEmpty()) {
            throw new Exception("Data already exists");
        }

        Statistical newStatistical = new Statistical();
        newStatistical.setReplicate(request.replicate);
        newStatistical.setSubReplicate(request.subReplicate);
        newStatistical.setDate(request.date);
        newStatistical.setLongitude(request.longitude);
        newStatistical.setLatitude(request.latitude);
        newStatistical.setChlorophyll(request.chlorophyll);
        newStatistical.setPConc(request.pConc);
        newStatistical.setKConc(request.kConc);
        newStatistical.setNConc(request.nConc);
        newStatistical.setWetWeight(request.wetWeight);
        newStatistical.setDriedWeight(request.driedWeight);
        newStatistical.setMoiture(request.moiture);
        newStatistical.setDigesion(request.digesion);
        newStatistical.setUserId(request.userId);

        return statisticalService.addData(newStatistical);
    }
    @GET
    @RolesAllowed({"USER"})
    @Path("/{id}")
    public Statistical getDataById(@PathParam("id") int id) throws DataNotFoundException {
        Statistical s = statisticalService.getDataById(id);
        if (s == null) {
            throw new DataNotFoundException("Not on Database");
        }
        return s;
    }

    @PUT
    @RolesAllowed({"USER"})
    @Path("/{id}")
    public Statistical updateData(@PathParam("id") int id, @Valid StatisticalDto statisticalDto) throws DataNotFoundException {
        if (statisticalService != null && statisticalDto != null) {
            return statisticalService.updateData(id, statisticalDto.toStatistical());
        } else {
            throw new IllegalArgumentException("Not on database");
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"USER"})
    public Response deleteStat(@PathParam("id") int id) throws DataNotFoundException {
        statisticalService.deleteStat(id);
        return Response.status(Response.Status.OK).build();
    }
}
