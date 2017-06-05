package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Simonas on 2017-01-25.
 */

public class ModuleRequest {
    @SerializedName("IDModule")
    private String moduleId;

    public ModuleRequest(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public ModuleRequest() {
    }
}
