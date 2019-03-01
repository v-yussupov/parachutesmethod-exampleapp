package org.parachutesmethod.exampleapp;

import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.parachutesmethod.annotations.ParachuteMethod;

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
    public ResponsePOJO capitalize(RequestPOJO input) {
        if (Objects.isNull(input)) {
            return new ResponsePOJO("input cannot be empty");
        }
        return new ResponsePOJO(input.getRequest().toUpperCase());
    }

    @POST
    @Path("operations/reverse")
    @Produces(MediaType.APPLICATION_JSON)
    @ParachuteMethod(
            retainParachuteAnnotations = true,
            overProvisioningFactor = 1.4,
            rerouteOnDay = "20191224"
    )
    public ResponsePOJO reverse(RequestPOJO input) {
        if (Objects.isNull(input)) {
            return new ResponsePOJO("input cannot be empty");
        }
        return new ResponsePOJO(UtilClass.reverseString(input.getRequest()));
    }

    public static class UtilClass {
        static String reverseString(String s) {
            return new StringBuilder(s).reverse().toString();
        }
    }

    public class RequestPOJO {
        String request;

        public RequestPOJO(String request) {
            this.request = request;
        }

        public String getRequest() {
            return request;
        }
    }

    public class ResponsePOJO {
        String response;

        public ResponsePOJO(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }
    }
}
