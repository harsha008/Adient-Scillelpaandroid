package lt.sciil.eLPA.activity.settings;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lt.sciil.eLPA.App;
import lt.sciil.eLPA.R;
import lt.sciil.eLPA.dagger.NonConfigurationScope;
import lt.sciil.eLPA.database.LanguageRepository;
import lt.sciil.eLPA.database.ModuleRepository;
import lt.sciil.eLPA.database.TranslationRepository;
import lt.sciil.eLPA.database.model.Language;
import lt.sciil.eLPA.database.model.ModuleData;
import lt.sciil.eLPA.database.model.ValueToTranslate;
import lt.sciil.eLPA.mvp.BasePresenter;
import lt.sciil.eLPA.utils.PrefManager;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageListRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageTranslationRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageTranslationResponse;
import lt.sciil.eLPA.ws.RetrofitModel.ModuleConfigurationVersionRequest;
import lt.sciil.eLPA.ws.RetrofitModel.ModuleListRequest;
import lt.sciil.eLPA.ws.SciilAPI;

@NonConfigurationScope
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final List<ModuleData> moduleList = new ArrayList<>();
    private final List<Language> languages = new ArrayList<>();
    private final ModuleRepository moduleRepository;
    private final PrefManager prefManager;
    private final SciilAPI sciilAPI;
    private final TranslationRepository translationRepository;
    private final LanguageRepository languageRepository;
    private Long newSelectedLanguage;
    private boolean firstSelect = true;

    @Inject
    public SettingsPresenter(ModuleRepository moduleRepository, PrefManager prefManager, SciilAPI sciilAPI, TranslationRepository translationRepository, LanguageRepository languageRepository) {
        this.moduleRepository = moduleRepository;
        this.prefManager = prefManager;
        this.sciilAPI = sciilAPI;
        this.translationRepository = translationRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public void downloadSelectedLanguageTranslations() {
        if (newSelectedLanguage != null)
            prefManager.setLgeId(newSelectedLanguage);
        getView().showProgressDialog(R.string.downloading, R.string.downloading_selected_language);
        Disposable disposable = Observable.fromIterable(getView().getHashMapResource(R.xml.values_to_translate).entrySet())
                .map(entry -> {
                    ValueToTranslate valueToTranslate = new ValueToTranslate();
                    valueToTranslate.setModule("lpa");
                    valueToTranslate.setSection("mobile");
                    valueToTranslate.setViewTag(entry.getKey());
                    valueToTranslate.setText(entry.getValue());
                    return valueToTranslate;
                })
                .toList()
                .flatMapObservable(valuesToTranslate -> {
                    Long lastSelectedLanguageCode = prefManager.getLgeId();
                    LanguageTranslationRequest languageTranslationRequest = new LanguageTranslationRequest();
                    languageTranslationRequest.setValuesToTranslate(valuesToTranslate);
                    languageTranslationRequest.setLanguageId(lastSelectedLanguageCode);
                    return sciilAPI.getLanguageTranslation(languageTranslationRequest);
                })
                .flatMapIterable(LanguageTranslationResponse::getTranslatedValues)
                .map(translatedValue -> {
                    translationRepository.createOrUpdate(translatedValue);
                    return translatedValue;
                })
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translatedValue -> {
                }, throwable -> {
                    getView().dismissProgressDialog();
                    getView().showError(throwable);
                }, () -> {
                    getView().dismissProgressDialog();
                    getView().finishActivity();

                });
        compositeDisposable.add(disposable);
    }

    public void selectProfile(ModuleData moduleData) {
        prefManager.setModuleData(moduleData);
        Long userLanguage = prefManager.getLgeId();
        if (userLanguage == -1 || !firstSelect) {
            userLanguage = prefManager.getModuleData().getLgeId();
        }
        firstSelect = false;
        getView().setLanguageSelection(userLanguage);
    }

    public void languageSelected(int position) {
        newSelectedLanguage = languages.get(position).getId();
    }

    public List<ModuleData> getModuleList() {
        return moduleList;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void doneTypingServerUrl() {
        if (getView().saveUrls()) {
            getModules();
        }
    }

    public void getModules() {
        String profileServerUrl = prefManager.getProfileServerUrl();
        getView().showProfileLayout(false);
        if (profileServerUrl.isEmpty())
            return;

        getView().showProgressDialog(R.string.downloading, R.string.downloading_modules);
        prefManager.setRetrofitBaseUrl(profileServerUrl);
        Disposable disposable = sciilAPI.getModuleConfigurationVersion(new ModuleConfigurationVersionRequest())
                .flatMap(moduleConfigurationVersion -> {
                    prefManager.setModuleConfigurationVersion(moduleConfigurationVersion.getData());
                    return sciilAPI.getLanguageList(new LanguageListRequest());
                })
                .doOnSuccess(languageListResponse -> {
                    languageRepository.deleteTable();
                    languageRepository.create(languageListResponse.getData());
                    languages.clear();
                    languages.addAll(languageListResponse.getData());
                })
                .flatMap(moduleConfigurationVersion -> sciilAPI.getModuleList(new ModuleListRequest()))
                .doOnSuccess(moduleListResponse -> {
                    moduleRepository.deleteTable();
                    moduleRepository.create(moduleListResponse.getData());
                })
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moduleListResponse -> {

                    moduleList.clear();
                    moduleList.addAll(moduleListResponse.getData());
                    getView().setModules(moduleListResponse.getData());

                    getView().dismissProgressDialog();
                    getView().showProfileLayout(true);
                    getView().notifyLanguagesChanged();
                    getView().notifyModulesChanged();

                    ModuleData moduleData = prefManager.getModuleData();
                    if (moduleList.contains(moduleData)) {
                        getView().setProfileText(moduleData);
                        selectProfile(moduleData);
                    } else {
                        moduleData = new ModuleData();
                        prefManager.setModuleData(moduleData);
                        getView().setProfileText(moduleData);
                    }

                }, throwable -> {
                    getView().dismissProgressDialog();
                    getView().showError(throwable);
                    Log.d("SettingsPresenter", throwable.getMessage(), throwable);
                    prefManager.setRetrofitBaseUrl(prefManager.getWebServiceUrl());
                });
        compositeDisposable.add(disposable);
    }
}
