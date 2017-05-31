package lt.sciil.eLPA.activity.auditList;

import android.support.annotation.StringRes;

import lt.sciil.eLPA.mvp.BaseView;


public interface AuditListView extends BaseView {

    void setPullToRefresh();

    void notifyItemsAdded(int size);

    void notifyItemChanged(int position);

    void notifyItemsRemoved(int size);

    void setOfflineMode(boolean isConnected);

    void openFilterDialog();

    void startBrowserActivity(String url);

    void showToast(@StringRes int resourceId, int length);

    void startAuditService();

    void showProgress(boolean show);

    void dismissFilterDialog();

    void setAuditInfoText(@StringRes int resourceId,boolean visible);
}
