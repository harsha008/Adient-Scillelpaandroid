package lt.adient.mobility.eLPA.activity.settings;

import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;

import java.util.List;
import java.util.Map;

import lt.adient.mobility.eLPA.database.model.ModuleData;
import lt.adient.mobility.eLPA.mvp.BaseView;


public interface SettingsView extends BaseView {
    void translateUI();

    void showError(Throwable throwable);

    void setModules(List<ModuleData> list);

    void notifyModulesChanged();

    void notifyLanguagesChanged();

    void setLanguageSelection(Long languageCode);

    void setProfileText(ModuleData moduleData);

    void showProgressDialog(@StringRes int tittle, @StringRes int message);

    void dismissProgressDialog();

    void finishActivity();

    void showProfileLayout(boolean show);

    boolean saveUrls();

    Map<String, String> getHashMapResource(@XmlRes int hashMapResId);
}
