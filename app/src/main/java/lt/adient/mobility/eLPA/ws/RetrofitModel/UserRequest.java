package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Simonas on 2017-01-25.
 */

public class UserRequest {
    @SerializedName("IDLge")
    private Long lgeId;
    @SerializedName("IDUser")
    private String userId;

    public UserRequest() {
    }

    public UserRequest(Long lgeId, String userId) {
        this.lgeId = lgeId;
        this.userId = userId;
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
