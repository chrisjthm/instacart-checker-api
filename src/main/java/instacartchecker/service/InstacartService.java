package instacartchecker.service;

import instacartchecker.model.AvailabilityResponse;

import java.io.IOException;
import java.util.function.Consumer;

public interface InstacartService {

    AvailabilityResponse checkForAvailability(String store, String cookie) throws IOException ;

    void searchForAvailability(String store, String cookie, Consumer<String> callback) throws IOException,InterruptedException;
}
