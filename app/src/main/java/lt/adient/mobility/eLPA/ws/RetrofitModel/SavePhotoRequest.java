package lt.adient.mobility.eLPA.ws.RetrofitModel;

/**
 * Created by harol on 2016-10-23.
 */

public class SavePhotoRequest {
    private String IDSession;
    private RequestModule Module;
    private RequestUser User;
    private String Parameter1;
    private String Parameter2;
    private String Parameter3;

    public SavePhotoRequest(){}

    public SavePhotoRequest(String idUser, String idModule, Long lgeId, String imageRef, String base64Image, String name) {
        this.User = new RequestUser(idUser, lgeId);
        this.Module = new RequestModule(idModule);
        this.Parameter1 = imageRef;
        this.Parameter2 = name;
        this.Parameter3 = base64Image;
    }

    public String getIDSession() {
        return IDSession;
    }

    public void setIDSession(String IDSession) {
        this.IDSession = IDSession;
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

    public String getParameter1() {
        return Parameter1;
    }

    public void setParameter1(String parameter1) {
        Parameter1 = parameter1;
    }

    public String getParameter2() {
        return Parameter2;
    }

    public void setParameter2(String parameter2) {
        Parameter2 = parameter2;
    }

    public String getParameter3() {
        return Parameter3;
    }

    public void setParameter3(String parameter3) {
        Parameter3 = parameter3;
    }
}
