package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;


public class SystemSwitchData {
    @SerializedName("Module")
    private String module;
    @SerializedName("Entry")
    private String entry;
    @SerializedName("Value")
    private String value;
    @SerializedName("Info")
    private String info;

    public SystemSwitchData() {
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
