package lt.sciil.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

public class SystemSwitchRequest {

    @SerializedName("IDSession")
    private String sessionId;
    @SerializedName("User")
    private UserRequest user;
    @SerializedName("Module")
    private ModuleRequest module;
    @SerializedName("Parameter1")
    private String section;
    @SerializedName("Parameter2")
    private String entry;
    @SerializedName("Parameter3")
    private String defaultValue;

    public SystemSwitchRequest() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public ModuleRequest getModule() {
        return module;
    }

    public void setModule(ModuleRequest module) {
        this.module = module;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
