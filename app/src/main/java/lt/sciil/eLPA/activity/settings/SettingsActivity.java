package lt.sciil.eLPA.activity.settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.sciil.eLPA.BuildConfig;
import lt.sciil.eLPA.R;
import lt.sciil.eLPA.adapter.LanguageAdapter;
import lt.sciil.eLPA.database.model.ModuleData;
import lt.sciil.eLPA.mvp.MvpActivity;
import lt.sciil.eLPA.utils.PrefManager;
import lt.sciil.eLPA.utils.Translator;
import lt.sciil.eLPA.utils.Utility;
import okhttp3.HttpUrl;


public class SettingsActivity extends MvpActivity<SettingsView, SettingsPresenter> implements SettingsView {

    @BindView(R.id.serverText)
    protected TextView serverText;
    @BindView(R.id.languageText)
    protected TextView languageText;
    @BindView(R.id.profileText)
    protected TextView profileText;

    @BindView(R.id.languageSpinner)
    protected Spinner languageSpinner;
    @BindView(R.id.selectLanguage)
    protected Button selectLanguageButton;
    @BindView(R.id.profileSpinner)
    protected AutoCompleteTextView profileAutocomplete;
    @BindView(R.id.serverUrl)
    protected EditText serverUrl;
    @BindView(R.id.selectServer)
    protected Button selectServer;
    @BindView(R.id.profileLayout)
    protected LinearLayout profileLayout;

    @Inject
    protected Translator translator;
    @Inject
    protected PrefManager prefManager;

    private ArrayAdapter<ModuleData> moduleAdapter;
    private LanguageAdapter languageAdapter;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getInjector().inject(this);
        getPresenter().attachView(this);

        //if (BuildConfig.FLAVOR.equals("adient_")) {
            serverUrl.setEnabled(false);
        //}

        moduleAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, getPresenter().getModuleList());
        profileAutocomplete.setThreshold(1);
        profileAutocomplete.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && profileAutocomplete.getError() == null) {
                profileAutocomplete.showDropDown();
            }
        });
        profileAutocomplete.setOnClickListener(v -> {
            profileAutocomplete.showDropDown();
        });
        profileAutocomplete.setAdapter(moduleAdapter);
        profileAutocomplete.setOnItemClickListener((parent, view, position, id) -> {
            getPresenter().selectProfile(moduleAdapter.getItem(position));
            Utility.hideKeyboardFrom(this, profileAutocomplete);
            profileAutocomplete.setError(null);
        });

        languageAdapter = new LanguageAdapter(this, R.layout.spinner_dropdown_item, getPresenter().getLanguages());
        languageSpinner.setAdapter(languageAdapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().languageSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectServer.setOnClickListener(v -> getPresenter().doneTypingServerUrl());
        serverUrl.setText(prefManager.getProfileServerUrl());
        serverUrl.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getPresenter().doneTypingServerUrl();
                return true;
            }
            return false;
        });
        selectLanguageButton.setOnClickListener(v -> {
            if (profileAutocomplete.getText().toString().isEmpty() || prefManager.getModuleName().isEmpty()) {
                profileAutocomplete.setError(translator.getTranslation(this, R.string.select_module));
                profileAutocomplete.requestFocus();
                return;
            }
            getPresenter().downloadSelectedLanguageTranslations();
        });
        getPresenter().getModules();
        translateUI();
    }

    @Override
    public void translateUI() {
        translator.translate(serverText, languageText, selectLanguageButton, selectServer, profileText);
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e("exception", throwable.getMessage(), throwable);
        if (throwable instanceof UnknownHostException) {
            showDialog(R.string.connection_error, R.string.no_internet_connection);
        } else {
            showDialog(R.string.connection_error, R.string.server_problem);
        }
    }

    public void showDialog(@StringRes int tittle, @StringRes int body) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomDialog)
                .setTitle(translator.getTranslation(this, tittle))
                .setMessage(translator.getTranslation(this, body))
                .setCancelable(false)
                .setPositiveButton(translator.getTranslation(this, R.string.ok), null)
                .create();
        alertDialog.show();
    }

    @Override
    public void setModules(List<ModuleData> list) {
//        moduleAdapter = null;
//        moduleAdapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, getPresenter().getModuleList());
//        profileAutocomplete.setAdapter(moduleAdapter);
        moduleAdapter.clear();
        moduleAdapter.addAll(list);
    }

    @Override
    public void notifyModulesChanged() {
        moduleAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyLanguagesChanged() {
        languageAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLanguageSelection(Long languageCode) {
        languageSpinner.setSelection(languageAdapter.getIndexByLanguageId(languageCode));
    }

    @Override
    public void setProfileText(ModuleData moduleData) {
        profileAutocomplete.setText(moduleData.getModuleName());
        profileAutocomplete.dismissDropDown();
    }

    @Override
    public void showProfileLayout(boolean show) {
        profileLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressDialog(@StringRes int tittle, @StringRes int message) {
        if (progressDialog != null)
            dismissProgressDialog();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(translator.getTranslation(this, tittle));
        progressDialog.setMessage(translator.getTranslation(this, message));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public boolean saveUrls() {
        String server = serverUrl.getText().toString();
        HttpUrl httpUrl = HttpUrl.parse(server);
        if (httpUrl == null) {
            serverUrl.setError(translator.getTranslation(this, R.string.invalid_server_url));
            return false;
        }
        prefManager.setProfileServerUrl(server);
        return true;
    }

    @Override
    public void finishActivity() {
        translator.setUpdateNeeded(true);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Map<String, String> getHashMapResource(@XmlRes int hashMapResId) {
        Map<String, String> map = new HashMap<>();
        XmlResourceParser parser = getResources().getXml(hashMapResId);

        String key = null, value = null;

        try {
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("string")) {
                        key = parser.getAttributeValue(null, "name");

                        if (null == key) {
                            parser.close();
                            return null;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("string")) {
                        map.put(key, value);
                        key = null;
                        value = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.getText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }
}
