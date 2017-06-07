package lt.adient.mobility.eLPA.database.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class TranslatedValue {

    @SerializedName("Entry")
    @DatabaseField(id = true)
    private String tag;
    @DatabaseField
    @SerializedName("Module")
    private String module;
    @DatabaseField
    @SerializedName("Section")
    private String section;
    @DatabaseField
    @SerializedName("Text")
    private String translatedText;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public TranslatedValue() {
    }

    public TranslatedValue(String tag, String translatedText) {
        this.tag = tag;
        this.translatedText = translatedText;
    }
}
