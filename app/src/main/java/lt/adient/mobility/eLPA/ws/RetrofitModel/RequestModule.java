package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;


public class RequestModule{
    @SerializedName("IDModule")
    private String moduleId;

    public RequestModule(String idModule){
        moduleId = idModule;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
