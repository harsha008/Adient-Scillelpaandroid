package lt.adient.mobility.eLPA.activity.auditList;

import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.dagger.NonConfigurationScope;
import lt.adient.mobility.eLPA.database.AuditRepository;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.AuditStatus;
import lt.adient.mobility.eLPA.database.model.FilterParameters;
import lt.adient.mobility.eLPA.events.AuditUploadedEvent;
import lt.adient.mobility.eLPA.events.ConnectivityChangeEvent;
import lt.adient.mobility.eLPA.events.FilterEvent;
import lt.adient.mobility.eLPA.mvp.BasePresenter;
import lt.adient.mobility.eLPA.utils.JsonHelper;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ResultCode;
import lt.adient.mobility.eLPA.ws.SciilAPI;

@NonConfigurationScope
public class AuditListPresenter extends BasePresenter<AuditListView> {

    private static final String TAG = AuditListPresenter.class.getSimpleName();
    private final List<Audit> auditList = new ArrayList<>();
    private final PrefManager prefManager;
    private final AuditRepository auditRepository;
    private final SciilAPI sciilAPI;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AuditListPresenter(PrefManager prefManager, AuditRepository auditRepository, SciilAPI sciilAPI) {
        this.prefManager = prefManager;
        this.auditRepository = auditRepository;
        this.sciilAPI = sciilAPI;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        if (!compositeDisposable.isDisposed())
            compositeDisposable.clear();
    }

    public void downloadItems() {
        int size = auditList.size();
        auditList.clear();
        getView().notifyItemsRemoved(size);
        downloadAudits(prefManager.getFilterPreference());
    }


    public void downloadAudits(FilterParameters parameters) {
        getView().showProgress(true);
        String idUser = prefManager.getUserId();
        String idModule = prefManager.getModuleId();
        Long lgeId = prefManager.getLgeId();

        AuditRequest auditRequest = new AuditRequest(parameters, idUser, lgeId, idModule);
        if (prefManager.isOffline()) {
            loadAuditsOffline(parameters);
            return;
        }
        Disposable disposable = sciilAPI.getAudits(auditRequest)
                .map(this::saveAudits)
                .flatMapIterable(audits -> audits)
                .toSortedList(this::sortAudit)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateAuditList,
                        throwable -> {
                            Log.e("AuditListActivity", throwable.getMessage(), throwable);
                            getView().showToast(R.string.server_problem, Toast.LENGTH_LONG);
                            loadAuditsOffline(parameters);
                            getView().showProgress(false);
                        });
        compositeDisposable.add(disposable);
    }

    public void checkIfTheresAnythingToUpdate() {
        Disposable disposable = Observable.just(auditRepository.getNotSyncedAudits())
                .filter(audits -> !audits.isEmpty())
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(audits -> {
                    getView().startAuditService();
                });
        compositeDisposable.add(disposable);
    }

    private void updateAuditList(List<Audit> audits) {
        auditList.addAll(audits);
        getView().notifyItemsAdded(auditList.size());
        getView().showProgress(false);
        if (prefManager.isOffline()) {
            getView().setAuditInfoText(R.string.filter_offline, audits.isEmpty());
        } else {
            getView().setAuditInfoText(R.string.no_planned_audits, audits.isEmpty());
        }
    }

    private List<Audit> saveAudits(AuditResponse auditResponse) throws SQLException {
        if (auditResponse.getResultCode() == ResultCode.SUCCESSFUL) {
            List<Audit> auditsNeedSync = auditRepository.getNotSyncedAudits();
            return Observable.fromIterable(auditResponse.getData())
                    .map(Audit::new)
                    .filter(audit -> audit.getAuditStatus() != AuditStatus.Audited && !auditsNeedSync.contains(audit))
                    .doOnNext(auditRepository::createOrUpdate)
                    .toList()
                    .blockingGet();
        }
        return Collections.emptyList();
    }


    public void loadAuditsOffline(FilterParameters parameters) {
        Disposable disposable = Observable.fromIterable(auditRepository.getAllAudits())
                .filter(audit -> audit.getMachineId().equals(parameters.getMachineId()) || parameters.getMachineId().isEmpty())
                .filter(audit -> audit.getUserId().equals(parameters.getUserId()) || parameters.getUserId().isEmpty())
                .filter(audit -> {
                    Long date = Long.parseLong(audit.getPlannedDate());
                    Long fromDate = JsonHelper.getDateFromPYPFormat(parameters.getPlanned().getDateFrom());
                    Long toDate = JsonHelper.getDateFromPYPFormat(parameters.getPlanned().getDateTo());
                    return date >= fromDate && date <= toDate;
                })
                .toSortedList(this::sortAudit)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateAuditList,
                        throwable -> {
                            Log.d(TAG, throwable.getMessage(), throwable);
                        });
        compositeDisposable.add(disposable);
    }

    private int sortAudit(Audit first, Audit second) {
        Long date1 = Long.parseLong(first.getPlannedDate());
        Long date2 = Long.parseLong(second.getPlannedDate());
        return date1 < date2 ? 1 : -1;
    }


    @Subscribe
    public void onFilterEvent(FilterEvent filterEvent) {
        int size = auditList.size();
        auditList.clear();
        getView().notifyItemsRemoved(size);
        getView().dismissFilterDialog();
        downloadAudits(filterEvent.getFilterParameters());
    }

    @Subscribe(sticky = true)
    public void onConnectivityChangeEvent(ConnectivityChangeEvent event) {
        getView().setOfflineMode(event.isConnected());
    }


    public void onFilterClick() {
        getView().openFilterDialog();
    }

    public void onDashboardClick() {
        String dashboardUrl = prefManager.getDashboardLink();
        if (dashboardUrl != null) {
            if (!dashboardUrl.startsWith("http://") && !dashboardUrl.startsWith("https://"))
                dashboardUrl = "http://" + dashboardUrl;
            getView().startBrowserActivity(dashboardUrl);
        }
    }

    public void onInfoClick() {
        String infoUrl = prefManager.getInfoLink();
        if (infoUrl != null) {
            if (!infoUrl.startsWith("http://") && !infoUrl.startsWith("https://"))
                infoUrl = "http://" + infoUrl;
            getView().startBrowserActivity(infoUrl);
        }
    }

    public List<Audit> getAuditList() {
        return auditList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAuditUploadedEvent(AuditUploadedEvent event) {
        if (auditList.contains(event.getAudit())) {
            int uploadedAuditIndex = auditList.indexOf(event.getAudit());
            auditList.set(uploadedAuditIndex, event.getAudit());
            getView().notifyItemChanged(uploadedAuditIndex);
        }
    }
}
