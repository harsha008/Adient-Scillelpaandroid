package lt.adient.mobility.eLPA.ws.RetrofitModel;


import com.google.gson.annotations.SerializedName;

public class BasicRequest {

    @SerializedName("IDSession")
    private String sessionId;
    @SerializedName("Module")
    private RequestModule module;
    @SerializedName("User")
    private RequestUser user;

    public BasicRequest(String idUser, Long lgeId, String idModule){
        module = new RequestModule(idModule);
        user = new RequestUser(idUser, lgeId);
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

    public RequestUser getUser() {
        return user;
    }

    public void setUser(RequestUser user) {
        this.user = user;
    }
}
