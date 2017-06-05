package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class User {

    @DatabaseField(id = true)
    @SerializedName("IDUser")
    private String userId;

    @DatabaseField
    @SerializedName("FirstName")
    private String firstName;

    @DatabaseField
    @SerializedName("IDLge")
    private int lgeId;

    @DatabaseField
    @SerializedName("LastName")
    private String lastname;

    @DatabaseField
    @SerializedName("UserID")
    private String userName;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getLgeId() {
        return lgeId;
    }

    public void setLgeId(int lgeId) {
        this.lgeId = lgeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
