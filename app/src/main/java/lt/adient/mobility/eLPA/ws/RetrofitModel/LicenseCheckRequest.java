package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

public class LicenseCheckRequest {
    @SerializedName("IDSession")
    private String sessionId;
    @SerializedName("User")
    private UserRequest userRequest;
    @SerializedName("Module")
    private ModuleRequest moduleRequest;
    @SerializedName("Parameter1")
    private String imei;

    public LicenseCheckRequest(String sessionId, UserRequest userRequest, ModuleRequest moduleRequest, String imei) {
        this.sessionId = sessionId;
        this.userRequest = userRequest;
        this.moduleRequest = moduleRequest;
        this.imei = imei;
    }

    public LicenseCheckRequest() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public ModuleRequest getModuleRequest() {
        return moduleRequest;
    }

    public void setModuleRequest(ModuleRequest moduleRequest) {
        this.moduleRequest = moduleRequest;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
