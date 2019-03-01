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
    @ParachuteMethod(backupRoute = true)
    public ResponsePOJO capitalize(RequestPOJO input) {
        if (Objects.isNull(input)) {
            return new ResponsePOJO("501", "input cannot be empty");
        }
        return new ResponsePOJO("200", input.getRequest().toUpperCase());
    }

    @POST
    @Path("operations/reverse")
    @Produces(MediaType.APPLICATION_JSON)
    @ParachuteMethod(backupRoute = true)
    public ResponsePOJO reverse(RequestPOJO input) {
        if (Objects.isNull(input)) {
            return new ResponsePOJO("501", "input cannot be empty");
        }
        return new ResponsePOJO("200", UtilClass.reverseString(input.getRequest()));
    }

    public static class UtilClass {
        static String reverseString(String s) {
            return new StringBuilder(s).reverse().toString();
        }
    }

    public class RequestPOJO {
        String request;

        public RequestPOJO() {
        }

        public RequestPOJO(String request) {
            this.request = request;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }
    }

    public class ResponsePOJO {
        String status;
        String body;

        public ResponsePOJO() {
        }

        public ResponsePOJO(String status, String body) {
            this.status = status;
            this.body = body;
        }

        public String getStatus() {
            return status;
        }

        public String getBody() {
            return body;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
