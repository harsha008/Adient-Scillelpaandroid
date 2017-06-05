package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;


public class Language {

    @DatabaseField
    @SerializedName("Active")
    private int active;
    @DatabaseField
    @SerializedName("DescPrefix")
    private String descriptionPrefix;
    @DatabaseField(id = true)
    @SerializedName("IDLge")
    private Long id;
    @DatabaseField
    @SerializedName("LgeDesc")
    private String languageName;
    @DatabaseField
    @SerializedName("LgeID")
    private String languageCode;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getDescriptionPrefix() {
        return descriptionPrefix;
    }

    public void setDescriptionPrefix(String descriptionPrefix) {
        this.descriptionPrefix = descriptionPrefix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public Language() {
    }
}
