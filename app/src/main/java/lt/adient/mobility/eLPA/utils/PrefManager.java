package lt.adient.mobility.eLPA.utils;

import android.content.SharedPreferences;

import lt.adient.mobility.eLPA.database.model.FilterParameters;
import lt.adient.mobility.eLPA.database.model.ModuleData;
import lt.adient.mobility.eLPA.database.model.PictureQuality;
import lt.adient.mobility.eLPA.database.model.Planned;
import lt.adient.mobility.eLPA.ws.BaseUrlInterceptor;
import lt.adient.mobility.eLPA.ws.RetrofitModel.BasicRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleConfigurationVersion;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.UserRequest;

public class PrefManager {

    private static final String FILTER_USER_ID = "FILTER_USER_ID";
    private static final String FILTER_MACHINE_ID = "FILTER_MACHINE_ID";
    private static final String FILTER_FROM_DATE = "FILTER_FROM_DATE";
    private static final String FILTER_TO_DATE = "FILTER_TO_DATE";


    public static final String PROFILE_SERVER_URL = "CENTRAL_SERVER_URL";
    public static final String WEB_SERVICE_URL = "WEB_SERVICE_URL";

    public static final String SERVER_URL_KEY = "SERVER_URL";
    public static final String DASHBOARD_LINK_KEY = "DASHBOARD_LINK";
    public static final String INFO_LINK_KEY = "INFO_LINK";

    private static final String LOGIN = "saveLogin";
    private static final String PASSWORD = "password";
    private static final String REMEMBER = "remember";
    private static final String FIRST_TIME = "FIRST_TIME";

    private static final String USER_ID = "USER_ID";
    private static final String MODULE_ID = "MODULE_ID";
    private static final String PROFILE_MODULE_ID = "PROFILE_MODULE_ID";
    private static final String LGE_ID = "LGE_ID_LONG";
    private static final String PROFILE_LGE_ID = "PROFILE_LGE_ID";
    private static final String OFFLINE_MODE = "offline_mode";

    private static final String MODULE_CONFIGURATION_VERSION = "MODULE_CONFIGURATION_VERSION";
    private static final String MODULE_NAME = "MODULE_NAME";
    private static final String MODULE_DESCRIPTION = "MODULE_DESCRIPTION";
    private static final String PICTURE_SIZE_SETTING = "PICTURE_SIZE_SETTING";
    private static final String FIRST_TIME_PERMISSION_REQUEST = "FIRST_TIME_PERMISSION_REQUEST";

    private final SharedPreferences preferences;
    private final BaseUrlInterceptor interceptor;

    public PrefManager(SharedPreferences preferences, BaseUrlInterceptor interceptor) {
        this.preferences = preferences;
        this.interceptor = interceptor;
    }

    public Boolean isFirstTimePermissionRequest() {
        Boolean firstTime = preferences.getBoolean(FIRST_TIME_PERMISSION_REQUEST, true);
        preferences.edit().putBoolean(FIRST_TIME_PERMISSION_REQUEST, false).apply();
        return firstTime;
    }

    public String getModuleConfigurationVersion() {
        return preferences.getString(MODULE_CONFIGURATION_VERSION, null);
    }

    public String getModuleName() {
        return preferences.getString(MODULE_NAME, "");
    }

    private void setModuleName(String moduleName) {
        preferences.edit().putString(MODULE_NAME, moduleName).apply();
    }

    private void setModuleDescription(String moduleDescription) {
        preferences.edit().putString(MODULE_DESCRIPTION, moduleDescription).apply();
    }

    private String getModuleDescription() {
        return preferences.getString(MODULE_DESCRIPTION, "");
    }

    public void setModuleConfigurationVersion(String version) {
        preferences.edit().putString(MODULE_CONFIGURATION_VERSION, version).apply();
    }

    public String getFilterUserId() {
        return preferences.getString(FILTER_USER_ID, "");
    }

    public void setFilterUserId(String filterUserId) {
        preferences.edit().putString(FILTER_USER_ID, filterUserId).apply();
    }

    public String getFilterMachineId() {
        return preferences.getString(FILTER_MACHINE_ID, "");
    }

    public void setFilterMachineId(String filterMachineId) {
        preferences.edit().putString(FILTER_MACHINE_ID, filterMachineId).apply();
    }

    public long getFilterFromDate() {
        return preferences.getLong(FILTER_FROM_DATE, 0);
    }

    public void setFilterFromDate(long filterFromDate) {
        preferences.edit().putLong(FILTER_FROM_DATE, filterFromDate).apply();
    }

    public long getFilterToDate() {
        return preferences.getLong(FILTER_TO_DATE, 0);
    }

    public void setFilterToDate(long filterToDate) {
        preferences.edit().putLong(FILTER_TO_DATE, filterToDate).apply();
    }

    public String getDashboardLink() {
        return preferences.getString(DASHBOARD_LINK_KEY, "");
    }

    public void setDashboardLink(String dashboardLink) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DASHBOARD_LINK_KEY, dashboardLink);
        editor.apply();
    }

    public String getInfoLink() {
        return preferences.getString(INFO_LINK_KEY, "");
    }

    public void setDocumentationLink(String documentationLink) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(INFO_LINK_KEY, documentationLink);
        editor.apply();
    }


    public void setRetrofitBaseUrl(String serverUrl) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SERVER_URL_KEY, serverUrl);
        editor.apply();
        interceptor.setBaseUrl(serverUrl);
    }

    private void setWebServiceUrl(String serverUrl) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(WEB_SERVICE_URL, serverUrl);
        editor.apply();
    }

    public void setProfileServerUrl(String serverUrl) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PROFILE_SERVER_URL, serverUrl);
        editor.apply();
    }

    public String getRetrofitBaseUrl() {
        return preferences.getString(SERVER_URL_KEY, "");
    }

    public String getWebServiceUrl() {
        return preferences.getString(WEB_SERVICE_URL, "");
    }

    public String getProfileServerUrl() {
        return preferences.getString(PROFILE_SERVER_URL, "");
    }

    public void saveLogin(String user, String password, Boolean rememberMe) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(REMEMBER, rememberMe);
        if (rememberMe) {
            editor.putString(LOGIN, user);
            editor.putString(PASSWORD, password);
        } else {
            editor.putString(LOGIN, null);
            editor.putString(PASSWORD, null);
        }
        editor.apply();
    }

    public String getLogin() {
        return preferences.getString(LOGIN, "");
    }

    public String getPassword() {
        return preferences.getString(PASSWORD, "");
    }

    public String getUserId() {
        return preferences.getString(USER_ID, "");
    }

    public void setUserId(String userId) {
        preferences.edit().putString(USER_ID, userId).apply();
    }

    public String getModuleId() {
        return preferences.getString(MODULE_ID, "");
    }

    public ModuleRequest getModuleRequest() {
        String moduleId = getModuleId();
        return new ModuleRequest(moduleId);
    }

    public UserRequest getUserRequest() {
        Long lgeId = getLgeId();
        String userId = getUserId();
        return new UserRequest(lgeId, userId);
    }

    public String getProfileModuleId() {
        return preferences.getString(PROFILE_MODULE_ID, "");
    }

    public void setModuleId(String moduleId) {
        preferences.edit().putString(MODULE_ID, moduleId).apply();
    }

    public void setProfileModuleId(String moduleId) {
        preferences.edit().putString(PROFILE_MODULE_ID, moduleId).apply();
    }

    public Long getLgeId() {
        return preferences.getLong(LGE_ID, -1L);
    }

    public void setLgeId(Long lgeId) {
        preferences.edit().putLong(LGE_ID, lgeId).apply();
    }

    public Long getProfileLgeId() {
        return preferences.getLong(PROFILE_LGE_ID, -1L);
    }

    private void setProfileLgeId(Long lgeId) {
        preferences.edit().putLong(PROFILE_LGE_ID, lgeId).apply();
    }

    public Boolean isOffline() {
        return preferences.getBoolean(OFFLINE_MODE, false);
    }

    public void setOffline(Boolean isOffline) {
        preferences.edit().putBoolean(OFFLINE_MODE, isOffline).apply();
    }

    public Boolean isFirstTime() {
        return preferences.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean firstTime) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_TIME, firstTime);
        editor.apply();
    }

    public Boolean isRememberMe() {
        return preferences.getBoolean(REMEMBER, false);
    }

    public Boolean isModuleConfigurationOutdated(ModuleConfigurationVersion moduleConfigurationVersion) {
        String version = getModuleConfigurationVersion();
        return version == null || !version.equals(moduleConfigurationVersion.getData());
    }

    public FilterParameters getFilterPreference() {
        FilterParameters filterParameters = new FilterParameters();
        String idUser = getFilterUserId();
        String idMachine = getFilterMachineId();
        Long fromDateMillis = getFilterFromDate();
        Long toDateMillis = getFilterToDate();
        Planned planned = new Planned(JsonHelper.formatPYPFormat(fromDateMillis), JsonHelper.formatPYPFormat(toDateMillis));
        filterParameters.setUserId(idUser);
        filterParameters.setMachineId(idMachine);
        filterParameters.setPlanned(planned);
        return filterParameters;
    }

    public void setFilterPreference(FilterParameters filterParameters, Long fromDate, Long toDate) {
        setFilterUserId(filterParameters.getUserId());
        setFilterMachineId(filterParameters.getMachineId());
        setFilterFromDate(fromDate);
        setFilterToDate(toDate);
    }

    public void setModuleData(ModuleData moduleData) {
        setDashboardLink(moduleData.getDashboardLink());
        setDocumentationLink(moduleData.getDocumentationLink());
        setRetrofitBaseUrl(moduleData.getWebServiceLink());
        setWebServiceUrl(moduleData.getWebServiceLink());
        setProfileLgeId(moduleData.getLgeId());
        setProfileModuleId(moduleData.getModuleId());
        setModuleName(moduleData.getModuleName());
        setModuleDescription(moduleData.getModuleDescription());
    }

    public ModuleData getModuleData() {
        ModuleData moduleData = new ModuleData();
        moduleData.setDashboardLink(getDashboardLink());
        moduleData.setDocumentationLink(getInfoLink());
        moduleData.setWebServiceLink(getWebServiceUrl());
        moduleData.setModuleName(getModuleName());
        moduleData.setModuleId(getProfileModuleId());
        moduleData.setLgeId(getProfileLgeId());
        moduleData.setModuleDescription(getModuleDescription());
        return moduleData;
    }

    public void setPictureSizeSetting(String value) {
        preferences.edit().putString(PICTURE_SIZE_SETTING, value).apply();
    }

    public PictureQuality getPictureSizeSetting() {
        String quality = preferences.getString(PICTURE_SIZE_SETTING, "Big");
        return PictureQuality.getPictureQuality(quality);
    }

    public BasicRequest getBasicRequest() {
        String idUser = getUserId();
        String idModule = getModuleId();
        Long lgeId = getLgeId();
        return new BasicRequest(idUser, lgeId, idModule);
    }
}
