package org.parachutesmethod.exampleapp;

import io.swagger.annotations.Api;
import org.parachutesmethod.annotations.ParachuteMethod;
import org.parachutesmethod.models.RequestPOJO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

@Path("/")
@Api(value = "mainresource", description = "Sample description")
public class MainResource {

    private static Logger LOGGER = LoggerFactory.getLogger(MainResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Example utility methods resource";
    }

    @POST
    @Path("operations/capitalize")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ParachuteMethod(backupRoute = true)
    public ResponsePOJO capitalize(RequestPOJO input) {
        if (Objects.isNull(input)) {
            return new ResponsePOJO("input cannot be empty");
        }
        return new ResponsePOJO(input.getRequest().toUpperCase());
    }

    @POST
    @Path("operations/reverse")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ParachuteMethod(backupRoute = true)
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

    public class ResponsePOJO {
        String response;

        public ResponsePOJO() {
        }

        public ResponsePOJO(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
