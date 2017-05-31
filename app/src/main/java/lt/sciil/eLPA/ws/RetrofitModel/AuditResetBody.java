package lt.sciil.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;


public class AuditResetBody {

    @SerializedName("Parameter1")
    private String auditId;
    @SerializedName("Module")
    private ModuleRequest moduleRequest;
    @SerializedName("User")
    private UserRequest userRequest;

    public AuditResetBody() {
    }

    public AuditResetBody(String auditId, ModuleRequest moduleRequest, UserRequest userRequest) {
        this.auditId = auditId;
        this.moduleRequest = moduleRequest;
        this.userRequest = userRequest;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public ModuleRequest getModuleRequest() {
        return moduleRequest;
    }

    public void setModuleRequest(ModuleRequest moduleRequest) {
        this.moduleRequest = moduleRequest;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
}
