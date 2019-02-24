package org.parachutesmethod.exampleapp;

import org.parachutesmethod.annotations.ParachuteMethod;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public String capitalize(String input) {
        if (Objects.isNull(input)) {
            return "input cannot be empty";
        }
        return input.toUpperCase();
    }

    @POST
    @Path("operations/reverse")
    @Produces(MediaType.APPLICATION_JSON)
    @ParachuteMethod(
            retainParachuteAnnotations = true,
            overProvisioningFactor = 1.4,
            rerouteOnDay = "20191224"
    )
    public ResponsePOJO reverse(String input) {
        if (Objects.isNull(input)) {
            return new ResponsePOJO(input, "ERROR");
        }
        return new ResponsePOJO(input, UtilClass.reverseString(input));
    }

    public static class UtilClass {
        static String reverseString(String s) {
            return new StringBuilder(s).reverse().toString();
        }
    }

    public class ResponsePOJO {
        String givenInput;
        String resultingOutput;

        public ResponsePOJO(String givenInput, String resultingOutput) {
            this.givenInput = givenInput;
            this.resultingOutput = resultingOutput;
        }

        public String getGivenInput() {
            return givenInput;
        }

        public void setGivenInput(String givenInput) {
            this.givenInput = givenInput;
        }

        public String getResultingOutput() {
            return resultingOutput;
        }

        public void setResultingOutput(String resultingOutput) {
            this.resultingOutput = resultingOutput;
        }
    }
}
