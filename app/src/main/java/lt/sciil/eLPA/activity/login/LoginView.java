package lt.sciil.eLPA.activity.login;


import android.content.DialogInterface;
import android.support.annotation.StringRes;

import lt.sciil.eLPA.mvp.BaseView;

public interface LoginView extends BaseView {
    void showError(Throwable throwable);

    void setConnected(Boolean connected);

    void launchMainActivity();

    void launchSettings();

    void showToast(@StringRes int resourceId, int length);

    void translateUI();

    void showProgressDialog(boolean show);

    void showDialog(@StringRes int tittle, @StringRes int message,@StringRes int buttonText, DialogInterface.OnClickListener onClickListener);

    void showModuleDownloadRetry();

    boolean isPermissionGranted();

    void showPermissionRationale();

    void closeApp();
}
