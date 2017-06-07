package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

import lt.adient.mobility.eLPA.database.model.FilterParameters;


public class AuditRequest {
    @SerializedName("IDSession")
    private String sessionId;
    @SerializedName("Module")
    private RequestModule module;
    @SerializedName("Parameter1")
    private FilterParameters filterParameters;
    @SerializedName("User")
    private RequestUser user;

    public AuditRequest() {
    }

    public AuditRequest(FilterParameters parameters, String idUser, Long lgeId, String idModule) {
        filterParameters = parameters;
        user = new RequestUser(idUser, lgeId);
        module = new RequestModule(idModule);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public RequestModule getModule() {
        return module;
    }

    public void setModule(RequestModule module) {
        this.module = module;
    }

    public FilterParameters getFilterParameters() {
        return filterParameters;
    }

    public void setFilterParameters(FilterParameters filterParameters) {
        this.filterParameters = filterParameters;
    }

    public RequestUser getUser() {
        return user;
    }

    public void setUser(RequestUser user) {
        this.user = user;
    }

}
