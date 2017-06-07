package lt.adient.mobility.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lt.adient.mobility.eLPA.database.model.TranslatedValue;

public class LanguageTranslationResponse {

    @SerializedName("Result1")
    private List<TranslatedValue> translatedValues;

    public List<TranslatedValue> getTranslatedValues() {
        return translatedValues;
    }

    public void setTranslatedValues(List<TranslatedValue> translatedValues) {
        this.translatedValues = translatedValues;
    }

    public LanguageTranslationResponse() {
    }
}
