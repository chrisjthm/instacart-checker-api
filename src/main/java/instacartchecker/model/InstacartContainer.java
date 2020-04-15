package instacartchecker.model;

import java.util.List;

public class InstacartContainer {

    private List<InstacartModule> modules;

    public InstacartContainer() {}

    public void setModules(List<InstacartModule> modules) {
        this.modules = modules;
    }

    public List<InstacartModule> getModules() {
        return modules;
    }
}
