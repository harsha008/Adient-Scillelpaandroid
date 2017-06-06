package lt.adient.mobility.eLPA.ws.RetrofitModel;

/**
 * Created by Haroldas on 10/17/2016.
 */

public class AuditQuestionRequest {

    private String IDSession = "NOTIMPL";
    private String Parameter1;
    private RequestModule Module;
    private RequestUser User;

    public AuditQuestionRequest() {

    }

    public AuditQuestionRequest(String parameter1, String module, String user, Long lgeId) {
        Module = new RequestModule(module);
        User = new RequestUser(user, lgeId);
        Parameter1 = parameter1;
    }

    public String getParameter1() {
        return Parameter1;
    }

    public void setParameter1(String parameter1) {
        Parameter1 = parameter1;
    }

    public RequestModule getModule() {
        return Module;
    }

    public void setModule(RequestModule module) {
        Module = module;
    }

    public RequestUser getUser() {
        return User;
    }

    public void setUser(RequestUser user) {
        User = user;
    }
}
