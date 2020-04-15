package instacartchecker.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsSenderImpl implements SmsSender {

    private static final Logger LOG = LoggerFactory.getLogger(SmsSenderImpl.class);

    private String twilioSid;
    private String twilioAuthToken;
    private String fromNumber;

    public SmsSenderImpl(String twilioSid, String twilioAuthToken, String fromNumber) {
        this.twilioSid = twilioSid;
        this.twilioAuthToken = twilioAuthToken;
        this.fromNumber = fromNumber;
    }

    public void sendSms(String toNumber, String text) {
        Twilio.init(twilioSid, twilioAuthToken);

        Message message = Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(this.fromNumber),
                text).create();

        LOG.info("Sent SMS with sid from {} to {} with text: {}",
                message.getSid(), fromNumber, toNumber, text);
    }
}
