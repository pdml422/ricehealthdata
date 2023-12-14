package vn.edu.usth.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ExceptionDataHandler implements ExceptionMapper<DataNotFoundException> {
    @Override
    public Response toResponse(DataNotFoundException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity("An error occured: " + exception.getMessage()).build();
    }
}
