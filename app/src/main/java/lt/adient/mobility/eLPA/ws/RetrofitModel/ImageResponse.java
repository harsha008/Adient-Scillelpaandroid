package lt.adient.mobility.eLPA.ws.RetrofitModel;


import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("Data64")
    private String asBase64;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("IDDoc")
    private String docId;

    public ImageResponse() {
    }

    public String getAsBase64() {
        return asBase64;
    }

    public void setAsBase64(String asBase64) {
        this.asBase64 = asBase64;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

}
