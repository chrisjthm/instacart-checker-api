package instacartchecker.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import instacartchecker.client.InstacartClient;
import instacartchecker.model.AvailabilityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.function.Consumer;

public class InstacartServiceImpl implements InstacartService {

    private static final Logger LOG = LoggerFactory.getLogger(InstacartServiceImpl.class);

    private static final String SOURCE = "web";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6)" +
            " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36";
    private static final int WAIT_INTERVAL_MILLIS = 60 * 1000;
    private static final int TIMES_TO_SEARCH = 3600;

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private InstacartClient instacartClient;

    @Inject
    public InstacartServiceImpl(InstacartClient instacartClient) {
        this.instacartClient = instacartClient;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AvailabilityResponse checkForAvailability(String store, String cookie) throws IOException {
        LOG.info("Calling instacart to check for availability");
        Response response = instacartClient.checkForAvailability(store, SOURCE, cookie, USER_AGENT);
        return parseAvailabilityResponse(response);
    }

    @Override
    public void searchForAvailability(String store, String cookie, Consumer<String> callback) throws IOException,InterruptedException {
        int timesSearched = 0;
        while (timesSearched < TIMES_TO_SEARCH) {
            AvailabilityResponse response = checkForAvailability(store, cookie);
            if (!response.isNotAvailable()) {
                LOG.info("Found availability, invoking callback");
                callback.accept(store);
                break;
            }
            LOG.info("No availability, will sleep before trying again.");
            timesSearched++;
            Thread.sleep(WAIT_INTERVAL_MILLIS);
        }
    }

    private AvailabilityResponse parseAvailabilityResponse(Response response) throws IOException{
        if (response.getStatus() > 400) {
            throw new WebApplicationException(response.readEntity(String.class));
        }
        String entity = response.readEntity(String.class);
        return mapper.readValue(entity, AvailabilityResponse.class);
    }

}
