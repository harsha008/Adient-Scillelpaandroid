package lt.sciil.eLPA.ws.RetrofitModel;


import com.google.gson.annotations.SerializedName;

public enum ResultCode {
    @SerializedName("0")
    SUCCESSFUL(0),
    @SerializedName("1")
    MODULE_NOT_FOUND(1),
    @SerializedName("2")
    WRONG_USERNAME_OR_PASSWORD(2),
    @SerializedName("3")
    SERIAL_ERROR(3),
    @SerializedName("-1")
    ERROR(-1);

    private int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
