package org.parachutesmethod.exampleapp;

import org.parachutesmethod.annotations.ParachuteMethod;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @ParachuteMethod(
            retainParachuteAnnotations = true,
            overProvisioningFactor = 1.4,
            rerouteOnDay = "20191224"
    )
    public Response capitalize(String input) {
        if (Objects.isNull(input)) {
            return Response.serverError().entity("input cannot be empty").build();
        }
        return Response.ok().entity(input.toUpperCase()).build();
    }

    @POST
    @Path("operations/reverse")
    @Produces(MediaType.TEXT_PLAIN)
    @ParachuteMethod(
            retainParachuteAnnotations = true,
            overProvisioningFactor = 1.4,
            rerouteOnDay = "20191224"
    )
    public Response reverse(String input) {
        if (Objects.isNull(input)) {
            return Response.serverError().entity("input cannot be empty").build();
        }
        return Response.ok().entity(UtilClass.reverseString(input)).build();
    }

    public static class UtilClass {
        static String reverseString(String s) {
            return new StringBuilder(s).reverse().toString();
        }
    }
}
