package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

public class RequestUser {

    @SerializedName("IDLge")
    private Long lgeId;
    @SerializedName("IDUser")
    private String userId;

    public RequestUser() {
    }

    public RequestUser(String idUser, Long lgeId) {
        userId = idUser;
        this.lgeId = lgeId;
    }

    public Long getLgeId() {
        return lgeId;
    }

    public void setLgeId(Long lgeId) {
        this.lgeId = lgeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

