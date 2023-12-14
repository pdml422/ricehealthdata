package vn.edu.usth.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionUserHandler implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("An error occured: " + exception.getMessage()).build();
    }
}
