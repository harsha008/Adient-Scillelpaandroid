package lt.sciil.eLPA.activity.auditList;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.sciil.eLPA.R;
import lt.sciil.eLPA.adapter.AuditListAdapter;
import lt.sciil.eLPA.fragment.FilterDialog;
import lt.sciil.eLPA.mvp.MvpActivity;
import lt.sciil.eLPA.service.SaveAuditService;
import lt.sciil.eLPA.utils.PrefManager;
import lt.sciil.eLPA.utils.Translator;

import static lt.sciil.eLPA.utils.Utility.showTranslatedToast;

public class AuditListActivity extends MvpActivity<AuditListView, AuditListPresenter> implements AuditListView {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.auditRecyclerView)
    RecyclerView auditRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.auditInfoText)
    TextView auditInfoText;
    @Inject
    Translator translator;
    @Inject
    PrefManager prefManager;

    private FilterDialog filterDialog;
    private AuditListAdapter auditListAdapter;
    private MenuItem offlineModeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_list);
        ButterKnife.bind(this);
        getInjector().inject(this);
        getPresenter().attachView(this);
        setSupportActionBar(toolbar);
        toolbar.setTag("title_activity_audit_list");
        auditListAdapter = new AuditListAdapter(getPresenter().getAuditList(), this);
        auditRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        auditRecyclerView.setAdapter(auditListAdapter);
        setPullToRefresh();
        getPresenter().checkIfTheresAnythingToUpdate();
        translate();

    }

    @Override
    public void setPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> getPresenter().downloadItems());
    }

    @Override
    public void notifyItemsAdded(int size) {
        auditListAdapter.notifyItemRangeInserted(0, size);
    }

    @Override
    public void notifyItemChanged(int position) {
        auditListAdapter.notifyItemChanged(position);
    }

    @Override
    public void notifyItemsRemoved(int size) {
        auditListAdapter.notifyItemRangeRemoved(0, size);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_audit_list, menu);
        MenuItem actionFilter = menu.findItem(R.id.action_filter);
        MenuItem actionInfo = menu.findItem(R.id.action_info);
        MenuItem actionDashboard = menu.findItem(R.id.action_dashboard);
        boolean offlineMode = prefManager.isOffline();
        offlineModeMenu = menu.findItem(R.id.action_wifi);
        if (offlineMode) {
            offlineModeMenu.setTitle(R.string.offline_mode_off);
            offlineModeMenu.setIcon(R.drawable.offline);
            translator.translateMenuItem(offlineModeMenu, "offline_mode_on");
        } else {
            offlineModeMenu.setTitle(R.string.offline_mode_on);
            offlineModeMenu.setIcon(R.drawable.online);
            translator.translateMenuItem(offlineModeMenu, "offline_mode_off");
        }
        translator.translateMenuItem(actionFilter, "filter");
        translator.translateMenuItem(actionInfo, "info");
        translator.translateMenuItem(actionDashboard, "dashboard");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                getPresenter().onFilterClick();
                return true;
            case R.id.action_info:
                getPresenter().onInfoClick();
                return true;
            case R.id.action_dashboard:
                getPresenter().onDashboardClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setOfflineMode(boolean isConnected) {
        if (offlineModeMenu != null)
            if (isConnected) {
                offlineModeMenu.setTitle(R.string.offline_mode_off);
                offlineModeMenu.setIcon(R.drawable.online);
                translator.translateMenuItem(offlineModeMenu, "offline_mode_off");
            } else {
                offlineModeMenu.setTitle(R.string.offline_mode_on);
                offlineModeMenu.setIcon(R.drawable.offline);
                translator.translateMenuItem(offlineModeMenu, "offline_mode_on");
            }
    }

    @Override
    public void openFilterDialog() {
        filterDialog = new FilterDialog();
        filterDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        filterDialog.show(getSupportFragmentManager(), "filterDialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().downloadItems();
    }

    private void translate() {
        translator.translate(toolbar, auditInfoText);
    }

    @Override
    public void startBrowserActivity(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        ComponentName componentName = i.resolveActivity(getPackageManager());
        if (componentName != null)
            startActivity(i);
    }

    @Override
    public void startAuditService() {
        Intent i = new Intent(getBaseContext(), SaveAuditService.class);
        startService(i);
    }

    @Override
    public void showToast(@StringRes int resourceId, int length) {
        showTranslatedToast(getBaseContext(), resourceId, length, translator);
    }

    @Override
    public void showProgress(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void dismissFilterDialog() {
        if (filterDialog != null)
            filterDialog.dismiss();
    }

    @Override
    public void setAuditInfoText(@StringRes int resourceId, boolean isVisible) {
        auditInfoText.setText(translator.getTranslation(this, resourceId));
        auditInfoText.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}
