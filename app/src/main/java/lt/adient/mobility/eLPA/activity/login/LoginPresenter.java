package lt.adient.mobility.eLPA.activity.login;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.database.ModuleRepository;
import lt.adient.mobility.eLPA.database.UserRepository;
import lt.adient.mobility.eLPA.database.WorkstationRepository;
import lt.adient.mobility.eLPA.database.model.ModuleData;
import lt.adient.mobility.eLPA.events.ConnectivityChangeEvent;
import lt.adient.mobility.eLPA.dagger.NonConfigurationScope;
import lt.adient.mobility.eLPA.mvp.BasePresenter;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.utils.Utility;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditorResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.BasicRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LicenseCheckRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoginRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoginResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.MachineResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleConfigurationVersionRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleListRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ResultCode;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SystemSwitchRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.UserRequest;
import lt.adient.mobility.eLPA.ws.SciilAPI;

@NonConfigurationScope
public class LoginPresenter extends BasePresenter<LoginView> {

    private final PrefManager prefManager;
    private final SciilAPI sciilAPI;
    private final ModuleRepository moduleRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final UserRepository userRepository;
    private final WorkstationRepository workstationRepository;

    @Inject
    public LoginPresenter(PrefManager prefManager, SciilAPI sciilAPI, ModuleRepository moduleRepository, UserRepository userRepository, WorkstationRepository workstationRepository) {
        this.prefManager = prefManager;
        this.sciilAPI = sciilAPI;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.workstationRepository = workstationRepository;
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
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    @Subscribe(sticky = true)
    public void onConnectivityChangeEvent(ConnectivityChangeEvent event) {
        getView().setConnected(event.isConnected());
    }

    public void loginClick(String username, String password, Boolean rememberMe) {
        String selectedModule = prefManager.getModuleName();
        prefManager.saveLogin(username, password, rememberMe);
        LoginRequest loginRequest = new LoginRequest(username, password, selectedModule);
        getView().showProgressDialog(true);
        Disposable disposable = sciilAPI.login(loginRequest)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLogin, throwable -> {
                    getView().showError(throwable);
                    getView().showProgressDialog(false);
                });
        compositeDisposable.add(disposable);
    }

    public void loadSavedProfile() {
        String savedModuleId = prefManager.getProfileModuleId();
        ModuleData moduleData = moduleRepository.get(savedModuleId);
        if (moduleData == null) {
            getView().showToast(R.string.module_not_found, Toast.LENGTH_LONG);
            getView().launchSettings();
        } else {
            loadUserModuleData(moduleData);
        }
    }

    private void loadUserModuleData(ModuleData moduleData) {
        prefManager.setModuleData(moduleData);
    }

    public void checkIfAppFirstTimeLaunch() {
        if (prefManager.isFirstTime()) {
            getView().launchSettings();
            prefManager.setFirstTime(false);
        } else {
            getView().translateUI();
            checkModuleConfigurationVersion();
        }
    }

    public void checkModuleConfigurationVersion() {
        String profileServerUrl = prefManager.getProfileServerUrl();
        if (profileServerUrl.isEmpty()) {
            getView().launchSettings();
            return;
        }

        prefManager.setRetrofitBaseUrl(profileServerUrl);
        Disposable disposable = sciilAPI.getModuleConfigurationVersion(new ModuleConfigurationVersionRequest())
//                .filter(moduleConfigurationVersion -> prefManager.isModuleConfigurationOutdated(moduleConfigurationVersion)
//                        || moduleRepository.isEmpty())
                .flatMap(moduleConfigurationVersion -> {
                    prefManager.setModuleConfigurationVersion(moduleConfigurationVersion.getData());
                    return sciilAPI.getModuleList(new ModuleListRequest());
                })
                .doOnSuccess(moduleListResponse -> {
                    moduleRepository.deleteTable();
                    moduleRepository.create(moduleListResponse.getData());
                })
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moduleListResponse -> {
                    prefManager.setRetrofitBaseUrl(prefManager.getWebServiceUrl());
                    loadSavedProfile();
                }, throwable -> {
                    getView().showError(throwable);
                    prefManager.setRetrofitBaseUrl(prefManager.getWebServiceUrl());
                    getView().showModuleDownloadRetry();
                });
        compositeDisposable.add(disposable);
    }


    private void downloadPictureSizeSetting() {
        SystemSwitchRequest systemSwitchRequest = new SystemSwitchRequest();
        systemSwitchRequest.setDefaultValue("Big");
        systemSwitchRequest.setEntry("tabletimagesize");
        systemSwitchRequest.setModule(new ModuleRequest(prefManager.getModuleId()));
        systemSwitchRequest.setSection("admin");
        systemSwitchRequest.setUser(new UserRequest(prefManager.getLgeId(), prefManager.getUserId()));
        sciilAPI.getSetting(systemSwitchRequest)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(systemSwitchResponse -> {
                    prefManager.setPictureSizeSetting(systemSwitchResponse.getData().getValue());
                }, getView()::showError);
    }

    public void completeLogin(LoginResponse loginResponse) {
        switch (loginResponse.getResultCode()) {
            case SUCCESSFUL:
                setInitialFilterValues(loginResponse);
                if (getView().isPermissionGranted()) {
                    checkLicense();
                } else {
                    getView().showProgressDialog(false);
                    getView().showPermissionRationale();
                }
                break;
            case MODULE_NOT_FOUND:
                getView().showProgressDialog(false);
                getView().showToast(R.string.module_not_found, Toast.LENGTH_LONG);
                break;
            case WRONG_USERNAME_OR_PASSWORD:
                getView().showProgressDialog(false);
                getView().showToast(R.string.wrong_password, Toast.LENGTH_LONG);
                break;
            case SERIAL_ERROR:
                getView().showProgressDialog(false);
                getView().showToast(R.string.no_user_rights_for_module, Toast.LENGTH_LONG);
            default:
                getView().showProgressDialog(false);
                break;
        }
    }

    public void checkLicense() {
        getView().showProgressDialog(true);
        String imei = Utility.getIMEI();
        String sessionId = "";
        ModuleRequest moduleRequest = prefManager.getModuleRequest();
        UserRequest userRequest = prefManager.getUserRequest();
        LicenseCheckRequest licenseCheckRequest = new LicenseCheckRequest(sessionId, userRequest, moduleRequest, imei);
        Disposable disposable = sciilAPI.checkLicense(licenseCheckRequest)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(licenseCheckResponse -> {
                    if (!licenseCheckResponse.getData()) {
                        getView().showProgressDialog(false);
                        getView().showDialog(R.string.license_tittle, R.string.invalid_license, R.string.close_app, (dialog, which) -> {
                            dialog.dismiss();
                            getView().closeApp();
                        });
                    } else {
                        downloadPictureSizeSetting();
                        downloadAuditorsAndMachines();
                    }
                }, throwable -> {
                    getView().showError(throwable);
                    getView().showProgressDialog(false);
                });
        compositeDisposable.add(disposable);
    }

    private void downloadAuditorsAndMachines() {
        BasicRequest basicRequest = prefManager.getBasicRequest();
        sciilAPI.getAuditors(basicRequest)
                .flatMap(auditorResponse -> {
                    saveAuditors(auditorResponse);
                    return sciilAPI.getMachines(basicRequest);
                })
                .doOnNext(this::saveWorkStations)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(machineResponse -> {
                    getView().showProgressDialog(false);
                    getView().launchMainActivity();
                }, throwable -> {
                    getView().showError(throwable);
                    getView().showProgressDialog(false);
                });
    }

    private void saveAuditors(AuditorResponse auditorResponse) throws SQLException {
        if (auditorResponse.getResultCode() == ResultCode.SUCCESSFUL) {
            userRepository.clearAllUsers();
            userRepository.create(auditorResponse.getData());
        }
    }

    private void saveWorkStations(MachineResponse machineResponse) throws SQLException {
        if (machineResponse.getResultCode() == ResultCode.SUCCESSFUL) {
            workstationRepository.clearAllWorkstations();
            workstationRepository.create(machineResponse.getData());
        }
    }

    private void setInitialFilterValues(LoginResponse loginResponse) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        prefManager.setUserId(loginResponse.getUserData().getUserId());
        prefManager.setFilterUserId(loginResponse.getUserData().getUserId());
        prefManager.setFilterMachineId(null);
        prefManager.setFilterFromDate(calendar.getTimeInMillis());
        prefManager.setModuleId(loginResponse.getModuleData().getModuleId());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        prefManager.setFilterToDate(calendar.getTimeInMillis());
    }

    public void onMenuSettingsClick() {
        getView().launchSettings();
    }

    public void permissionGranted() {
        checkLicense();
    }

    public void permissionDenied() {
        getView().showDialog(R.string.permission_denied, R.string.permission_error, R.string.close_app, (dialog, which) -> {
            dialog.dismiss();
            getView().closeApp();
        });
    }
}
