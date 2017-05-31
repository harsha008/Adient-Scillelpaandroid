package lt.sciil.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

public class Response<T> {

    @SerializedName("Message")
    private String message;
    @SerializedName("ResultCode")
    private ResultCode resultCode;
    @SerializedName("Result1")
    private T data;

    public Response() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
