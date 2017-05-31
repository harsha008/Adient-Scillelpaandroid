package lt.sciil.eLPA.database.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harol on 2016-10-31.
 */

public class Modules {
    private List<String> modules = new ArrayList<>();

    public Modules(){

    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }
}
