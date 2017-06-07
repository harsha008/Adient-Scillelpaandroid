package lt.adient.mobility.eLPA.ws.RetrofitModel;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import lt.adient.mobility.eLPA.database.model.ValueToTranslate;

public class LanguageTranslationRequest {

    @SerializedName("Parameter1")
    private long languageId;
    @SerializedName("Parameter2")
    private List<ValueToTranslate> valuesToTranslate;

    public LanguageTranslationRequest() {
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    public List<ValueToTranslate> getValuesToTranslate() {
        return valuesToTranslate;
    }

    public void setValuesToTranslate(List<ValueToTranslate> valuesToTranslate) {
        this.valuesToTranslate = valuesToTranslate;
    }
}
