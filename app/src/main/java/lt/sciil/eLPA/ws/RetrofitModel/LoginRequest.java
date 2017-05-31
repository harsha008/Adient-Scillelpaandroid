package lt.sciil.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("Parameter1")
    private String userName;
    @SerializedName("Parameter2")
    private String password;
    @SerializedName("Parameter3")
    private String module;

    public LoginRequest() {
    }

    public LoginRequest(String userName, String password, String module) {
        this.userName = userName;
        this.password = password;
        this.module = module;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
