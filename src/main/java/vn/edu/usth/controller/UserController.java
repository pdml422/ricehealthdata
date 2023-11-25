package vn.edu.usth.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import vn.edu.usth.dto.UserDto;
import vn.edu.usth.service.UserService;
import vn.edu.usth.model.User;
import vn.edu.usth.exception.UserNotFoundException;
import vn.edu.usth.exceptionhandler.ExceptionHandler;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "Basic Auth", type = SecuritySchemeType.HTTP, scheme = "basic")
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
//    @RolesAllowed({"USER", "ADMIN"})
    @Operation(summary = "Gets users", description = "Lists all available users")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "500", description = "Please insert user data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionHandler.class)))
    })
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GET
//    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    @Operation(summary = "Gets a user", description = "Retrieves a user by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "500", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionHandler.class)))
    })
    public User getUser(@PathParam("id") int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @PUT
//    @RolesAllowed({"USER", "ADMIN"})
    @Path("/{id}")
    @Operation(summary = "Update a user", description = "Update a user by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "500", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionHandler.class)))
    })
    public User updateUser(@PathParam("id") int id, @Valid UserDto userDto) throws UserNotFoundException {
        if (userService != null && userDto != null) {
            return userService.updateUser(id, userDto.toUser());
        } else {
            throw new IllegalArgumentException("userService or create user is null");
        }
    }

    @POST
    @PermitAll
    @Operation(summary = "Add a user", description = "create a user and add persists into database")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class))),
            @APIResponse(responseCode = "500", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ExceptionHandler.class)))
    })
    public User addUser(@Valid UserDto userDto) throws UserNotFoundException {
        return userService.addUser(userDto.toUser());
    }

    @DELETE
    @Path("/{id}")
    //    @RolesAllowed({"USER", "ADMIN"})
    public Response deleteUser(@PathParam("id") int id) throws UserNotFoundException {
        userService.deleteUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

