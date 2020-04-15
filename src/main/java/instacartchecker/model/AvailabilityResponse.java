package instacartchecker.model;

public class AvailabilityResponse {

    private static final String NOT_AVAILABLE_TITLE = "No delivery times available";
    private InstacartContainer container;

    public AvailabilityResponse() {}

    public InstacartContainer getContainer() {
        return container;
    }

    public void setContainer(InstacartContainer container) {
        this.container = container;
    }

    public boolean isNotAvailable() {
        return this.container != null
                && this.container.getModules() != null && !this.container.getModules().isEmpty()
                && this.container.getModules().get(0).getData() != null &&
                this.container.getModules().get(0).getData().getTitle() != null &&
                this.container.getModules().get(0).getData().getTitle().equals(NOT_AVAILABLE_TITLE);
    }

}
