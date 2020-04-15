package instacartchecker;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class InstacartCheckerConfiguration extends Configuration {

    private final String INSTACART_URL = "https://instacart.com";
    private final int DEFAULT_WORKER_POOL_SIZE = 10;

    private Integer workerPoolSize;
    private String instacartUrl;
    private String fromPhoneNumber;
    private String twilioAccountSID;
    private String twilioAuthToken;

    public String getInstacartUrl() {
        return StringUtils.defaultString(instacartUrl, INSTACART_URL);
    }

    public int getWorkerPoolSize() {
        return workerPoolSize != null ? workerPoolSize : DEFAULT_WORKER_POOL_SIZE;
    }

    public String getFromPhoneNumber() {
        return StringUtils.defaultString(System.getenv("FROM_PHONE_NUMBER"), fromPhoneNumber);
    }

    public String getTwilioAccountSID() {
        return StringUtils.defaultString(System.getenv("TWILIO_ACCOUNT_SID"), twilioAccountSID);
    }

    public String getTwilioAuthToken() {
        return StringUtils.defaultString(System.getenv("TWILIO_AUTH_TOKEN"), twilioAuthToken);
    }
}
