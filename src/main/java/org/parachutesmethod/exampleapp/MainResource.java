package org.parachutesmethod.exampleapp;

import org.parachutesmethod.annotations.ParachuteMethod;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/")
public class MainResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Example utility methods resource";
    }

    @POST
    @Path("operations/capitalize")
    @Produces(MediaType.TEXT_PLAIN)
    @ParachuteMethod(targetProvider = ParachuteMethod.Provider.AWS_LAMBDA)
    public Response capitalize(String input) {
        if (Objects.isNull(input)) {
            return Response.serverError().entity("input cannot be empty").build();
        }
        return Response.ok().entity(input.toUpperCase()).build();
    }

}
