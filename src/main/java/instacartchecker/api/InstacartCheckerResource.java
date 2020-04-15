package instacartchecker.api;

import instacartchecker.model.AvailabilityRequest;
import instacartchecker.service.InstacartService;
import instacartchecker.service.SmsSender;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.function.Consumer;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstacartCheckerResource {

    private static final Logger LOG = LoggerFactory.getLogger(InstacartCheckerResource.class);

    private InstacartService instacartService;
    private Vertx vertx;
    private SmsSender smsSender;

    @Inject
    public InstacartCheckerResource(@Named("instacartService") InstacartService instacartService,
                                    @Named("vertx") Vertx vertx,
                                    @Named("smsSender") SmsSender smsSender) {
        this.instacartService = instacartService;
        this.vertx = vertx;
        this.smsSender = smsSender;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/checkAndNotify")
    public Response checkForAvailability(
            @HeaderParam("Cookie") String cookie,
            AvailabilityRequest request) {
        Validate.notNull(request, "Must submit valid payload");
        LOG.info("Accepted request with store {} and phoneNumber {}", request.getStore(), request.getPhoneNumber());
        String store = request.getStore();
        String phoneNumber = request.getPhoneNumber();
        Validate.notBlank(store, "\"store\" must not be blank");
        Validate.notBlank(cookie, "\"Cookie\" must not be blank");
        Validate.notBlank(phoneNumber, "\"phoneNumber\" must not be blank");
        Validate.isTrue(isValidPhoneNumber(phoneNumber), "%s is not a valid phone number", phoneNumber);
        vertx.executeBlocking(future -> {
            try {
                String smsText = String.format("Hi, Instacart Checker here. We found some delivery time slots at %s. " +
                        "Hurry up and grab your slot before they are gone!", store);
                Consumer<String> smsCallback = (s) -> smsSender.sendSms(phoneNumber, smsText);
                instacartService.searchForAvailability(store, cookie, smsCallback);
                future.complete();
            } catch (Exception e) {
                LOG.error("Exception while executing async process ", e);
            }
        }, this::handleAsyncResult);
        return Response.accepted().build();
    }

    private void handleAsyncResult(AsyncResult result) {
        LOG.info("Completed async process");
    }

    private boolean isValidPhoneNumber(String number) {
        return number.length() == 12 && number.startsWith("+1");
    }

}
