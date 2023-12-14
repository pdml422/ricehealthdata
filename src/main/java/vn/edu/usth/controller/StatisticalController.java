package vn.edu.usth.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import vn.edu.usth.exception.ExceptionDataHandler;
import vn.edu.usth.model.Statistical;
import vn.edu.usth.payload.SearchStatistical;
import vn.edu.usth.service.data.DefaultStatisticalService;

import java.util.List;

@RequestScoped
@Path("/statistical")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class StatisticalController {
    private final DefaultStatisticalService statisticalService;

    @Inject
    public StatisticalController(DefaultStatisticalService statisticalService) {
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
    public List<Statistical> search(@RequestBody SearchStatistical searchStatistical) {
        return statisticalService.search(searchStatistical);
    }
}
