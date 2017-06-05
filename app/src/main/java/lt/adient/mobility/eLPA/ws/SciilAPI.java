package lt.adient.mobility.eLPA.ws;

import io.reactivex.Observable;
import io.reactivex.Single;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditQuestionRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditQuestionResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditResetBody;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditResetResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.BasicRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditorResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LanguageListRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LanguageListResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LanguageTranslationRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LanguageTranslationResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LicenseCheckRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LicenseCheckResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoadImageRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoadImageResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoginRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoginResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.MachineResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SystemSwitchRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SystemSwitchResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleListRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleListResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleConfigurationVersionRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ModuleConfigurationVersion;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SaveAuditRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SaveAuditResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SavePhotoRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SavePhotoResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SciilAPI {

    @POST("CommWebService/Web/UserValidate")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("CommWebService/Web/LicenseCheck")
    Single<LicenseCheckResponse> checkLicense(@Body LicenseCheckRequest licenseCheckRequest);

    @POST("LPAWebService/Web/LoadAuditsList")
    Observable<AuditResponse> getAudits(@Body AuditRequest auditRequest);

    @POST("LPAWebService/Web/LoadAuditors")
    Observable<AuditorResponse> getAuditors(@Body BasicRequest basicRequest);

    @POST("LPAWebService/Web/LoadMachines")
    Observable<MachineResponse> getMachines(@Body BasicRequest basicRequest);

    @POST("LPAWebService/Web/LoadLPAAudit")
    Observable<AuditQuestionResponse> getAuditQuestions(@Body AuditQuestionRequest auditQuestionRequest);

    @POST("CommWebService/Web/GetImageBase64")
    Observable<LoadImageResponse> getPhoto(@Body LoadImageRequest loadImageRequest);

    @POST("CommWebService/Web/SaveImageBase64")
    Observable<SavePhotoResponse> savePhoto(@Body SavePhotoRequest savePhotoRequest);

    @POST("LPAWebService/Web/SaveLPAAudit")
    Single<SaveAuditResponse> saveAudit(@Body SaveAuditRequest saveAuditRequest);

    @POST("LPAWebService/Web/ResetLPAAuditStatus")
    Observable<AuditResetResponse> resetAuditStatus(@Body AuditResetBody auditResetBody);

    @POST("CommWebService/Web/LoadLge")
    Single<LanguageListResponse> getLanguageList(@Body LanguageListRequest languageListRequest);

    @POST("CommWebService/Web/TranslateTexts")
    Observable<LanguageTranslationResponse> getLanguageTranslation(@Body LanguageTranslationRequest languageTranslationRequest);

    @POST("CommWebService/Web/LoadModuleConfigurationVersion")
    Single<ModuleConfigurationVersion> getModuleConfigurationVersion(@Body ModuleConfigurationVersionRequest moduleConfigurationVersionRequest);

    @POST("CommWebService/Web/GetModuleList")
    Single<ModuleListResponse> getModuleList(@Body ModuleListRequest moduleListRequest);

    @POST("CommWebService/Web/GetSystemSwitch")
    Single<SystemSwitchResponse> getSetting(@Body SystemSwitchRequest systemSwitchRequest);

}
