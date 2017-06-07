package lt.adient.mobility.eLPA.activity.login;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eggheadgames.siren.Siren;
import com.eggheadgames.siren.SirenAlertType;
import com.eggheadgames.siren.SirenVersionCheckType;

import java.net.UnknownHostException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.activity.auditList.AuditListActivity;
import lt.adient.mobility.eLPA.activity.settings.SettingsActivity;
import lt.adient.mobility.eLPA.database.model.ViewToTranslate;
import lt.adient.mobility.eLPA.exception.InvalidWebServiceUrl;
import lt.adient.mobility.eLPA.mvp.MvpActivity;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.utils.Translator;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static lt.adient.mobility.eLPA.utils.Utility.showTranslatedToast;

public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    private static final String TAG = "LoginActivity";
    private static final int LANGUAGE_REQUEST = 9999;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    @BindView(R.id.input_user)
    protected EditText userEditText;
    @BindView(R.id.input_password)
    protected EditText passwordEditText;
    @BindView(R.id.chkRemember)
    protected CheckBox rememberCheckbox;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.loginButton)
    protected Button loginButton;
    @BindView(R.id.connected)
    protected ImageView connectedCheckbox;
    @Inject
    protected Translator translator;
    @Inject
    protected PrefManager prefManager;
    private ProgressDialog loginProgressDialog;
    private static final String SIREN_JSON_DOCUMENT_URL = "https://ag.adient.com/mobile/android/eLPA/manifest.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getInjector().inject(this);
        getPresenter().attachView(this);
        toolbar.setTag("app_name");
        setSupportActionBar(toolbar);
        rememberCheckbox.setChecked(prefManager.isRememberMe());
        userEditText.setText(prefManager.getLogin());
        passwordEditText.setText(prefManager.getPassword());
        getPresenter().checkIfAppFirstTimeLaunch();

        Siren siren = Siren.getInstance(getApplicationContext());
        siren.setVersionCodeUpdateAlertType(SirenAlertType.FORCE);
        siren.checkVersion(this, SirenVersionCheckType.IMMEDIATELY, SIREN_JSON_DOCUMENT_URL);
    }

    @Override
    public void launchSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, LANGUAGE_REQUEST);
    }

    @Override
    public void showToast(@StringRes int resourceId, int length) {
        showTranslatedToast(getApplicationContext(), resourceId, length, translator);
    }

    @OnClick(R.id.loginButton)
    public void login(View view) {
        String userName = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        getPresenter().loginClick(userName, password, rememberCheckbox.isChecked());
    }

    @Override
    public void translateUI() {
        translator.translate(loginButton, rememberCheckbox, toolbar, userEditText, passwordEditText);
        invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LANGUAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                translateUI();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        MenuItem settingsItem = menu.findItem(R.id.action_settings);
        translator.translate(new ViewToTranslate<>(settingsItem, "settings"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                getPresenter().onMenuSettingsClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showModuleDownloadRetry() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomDialog)
                .setTitle(translator.getTranslation(this, R.string.connection_error))
                .setMessage(translator.getTranslation(this, R.string.could_not_download_profiles))
                .setCancelable(false)
                .setPositiveButton(translator.getTranslation(this, R.string.retry_download), (dialog, which) -> {
                    dialog.dismiss();
                    getPresenter().checkModuleConfigurationVersion();
                })
                .setNegativeButton(translator.getTranslation(this, R.string.close_app), (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setNeutralButton(translator.getTranslation(this, R.string.settings), (dialog, which) -> {
                    dialog.dismiss();
                    launchSettings();
                })
                .create();
        alertDialog.show();
    }

    private void showInvalidUrlDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomDialog)
                .setTitle(translator.getTranslation(this, R.string.connection_error))
                .setMessage(translator.getTranslation(this, R.string.invalid_web_service_url))
                .setCancelable(false)
                .setPositiveButton(translator.getTranslation(this, R.string.settings), (dialog, which) -> {
                    launchSettings();
                })

                .setNegativeButton(translator.getTranslation(this, R.string.close_app), (dialog, which) -> {
                    finish();
                })
                .setNeutralButton(translator.getTranslation(this, R.string.ok), (dialog, which) -> {

                })
                .create();
        alertDialog.show();
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e("exception", throwable.getMessage(), throwable);
        if (throwable instanceof UnknownHostException) {
            showTranslatedToast(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_LONG, translator);
        } else if (throwable instanceof SecurityException) {
            showTranslatedToast(getApplicationContext(), R.string.android_permission_exception, Toast.LENGTH_LONG, translator);
        } else if (throwable instanceof InvalidWebServiceUrl) {
            showInvalidUrlDialog();
        } else {
            showTranslatedToast(getApplicationContext(), R.string.server_problem, Toast.LENGTH_LONG, translator);
        }
    }

    @Override
    public void setConnected(Boolean connected) {
        if (connected) {
            connectedCheckbox.setImageResource(R.drawable.connected);
        } else {
            connectedCheckbox.setImageResource(R.drawable.disconnected);
        }
    }

    @Override
    public void launchMainActivity() {
        Intent intent = new Intent(this, AuditListActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressDialog(boolean show) {
        if (show) {
            if (loginProgressDialog == null) {
                loginProgressDialog = new ProgressDialog(this, R.style.CustomDialog);
                loginProgressDialog.setTitle(translator.getTranslation(this, R.string.logging_in));
                loginProgressDialog.setMessage(translator.getTranslation(this, R.string.please_wait));
                loginProgressDialog.setCancelable(false);
                loginProgressDialog.show();
            }
        } else {
            if (loginProgressDialog != null) {
                loginProgressDialog.dismiss();
                loginProgressDialog = null;
            }
        }
    }

    @Override
    public void showDialog(@StringRes int tittle, @StringRes int body, @StringRes int buttonText, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomDialog)
                .setTitle(translator.getTranslation(this, tittle))
                .setMessage(translator.getTranslation(this, body))
                .setCancelable(false)
                .setPositiveButton(translator.getTranslation(this, buttonText), onClickListener)
                .create();
        alertDialog.show();
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public boolean isPermissionGranted() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        return permissionCheck == PERMISSION_GRANTED;
    }

    @Override
    public void showPermissionRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                || prefManager.isFirstTimePermissionRequest()) {
            showDialog(R.string.permission_tittle, R.string.permission_phone_state, R.string.ok, (dialog, which) -> {
                dialog.dismiss();
                requestPermission();
            });
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPresenter().permissionGranted();
                } else {
                    getPresenter().permissionDenied();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
