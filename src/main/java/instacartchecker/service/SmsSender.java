package instacartchecker.service;

public interface SmsSender {

    void sendSms(String toNumber, String text);
}
