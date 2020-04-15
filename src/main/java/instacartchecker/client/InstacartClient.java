package instacartchecker.client;

import com.fasterxml.jackson.databind.util.JSONPObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface InstacartClient {

    @GET
    @Path("/v3/containers/{store}/next_gen/retailer_information/content/delivery")
    @Produces(value = { MediaType.APPLICATION_JSON })
    public Response checkForAvailability(
            @PathParam("store") String store,
            @QueryParam("source") String source,
            @HeaderParam("cookie") String cookie,
            @HeaderParam("user-agent") String userAgent);

}
