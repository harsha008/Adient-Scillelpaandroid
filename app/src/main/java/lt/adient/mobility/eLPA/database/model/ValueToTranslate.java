package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Simonas on 2017-01-04.
 */

public class ValueToTranslate {

    @SerializedName("Module")
    private String module;
    @SerializedName("Section")
    private String section;
    @SerializedName("Entry")
    private String viewTag;
    @SerializedName("Text")
    private String text;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getViewTag() {
        return viewTag;
    }

    public void setViewTag(String viewTag) {
        this.viewTag = viewTag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ValueToTranslate() {
    }
}
