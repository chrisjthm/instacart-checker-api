package instacartchecker.model;

public class AvailabilityRequest {

    private String store;
    private String phoneNumber;

    public AvailabilityRequest() {}

    public AvailabilityRequest(String store, String phoneNumber) {
        this.store = store;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStore() {
        return store;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
