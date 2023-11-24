package vn.edu.usth.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.resource.spi.ConfigProperty;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.usth.util.PBKDF2Encoder;
import vn.edu.usth.model.User;
import vn.edu.usth.dto.AuthRequest;
import vn.edu.usth.dto.AuthResponse;
import vn.edu.usth.util.TokenUtils;

@Path("/auth")
public class AuthController {
    @Inject
    PBKDF2Encoder passwordEncoder;

    public Long duration;
    public String issuer;

    @PermitAll
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {

        User u = User.findByUsername(authRequest.username);
        if (u != null && u.getPassword().equals(passwordEncoder.encode(authRequest.password))) {
            try {
                return Response.ok(new AuthResponse(TokenUtils.generateToken(u.getUsername(), u.getRole(), duration, issuer))).build();
            } catch (Exception e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
