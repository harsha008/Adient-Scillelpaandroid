package lt.sciil.eLPA.ws.RetrofitModel;

/**
 * Created by Haroldas on 10/18/2016.
 */

public class LoadImageRequest {
    private String IDSession = "NOTIMPL";
    private RequestUser User;
    private RequestModule Module;
    private String Parameter1;

    public LoadImageRequest(String doc, String idUser, Long lgeId, String idModule){
        User = new RequestUser(idUser, lgeId);
        Module = new RequestModule(idModule);
        Parameter1 = doc;
    }

    public String getIDSession() {
        return IDSession;
    }

    public void setIDSession(String IDSession) {
        this.IDSession = IDSession;
    }

    public RequestUser getUser() {
        return User;
    }

    public void setUser(RequestUser user) {
        User = user;
    }

    public RequestModule getModule() {
        return Module;
    }

    public void setModule(RequestModule module) {
        Module = module;
    }

    public String getParameter1() {
        return Parameter1;
    }

    public void setParameter1(String parameter1) {
        Parameter1 = parameter1;
    }
}
