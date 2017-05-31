package lt.sciil.eLPA.ws;

import io.reactivex.Observable;
import io.reactivex.Single;
import lt.sciil.eLPA.ws.RetrofitModel.AuditQuestionRequest;
import lt.sciil.eLPA.ws.RetrofitModel.AuditQuestionResponse;
import lt.sciil.eLPA.ws.RetrofitModel.AuditRequest;
import lt.sciil.eLPA.ws.RetrofitModel.AuditResetBody;
import lt.sciil.eLPA.ws.RetrofitModel.AuditResetResponse;
import lt.sciil.eLPA.ws.RetrofitModel.AuditResponse;
import lt.sciil.eLPA.ws.RetrofitModel.BasicRequest;
import lt.sciil.eLPA.ws.RetrofitModel.AuditorResponse;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageListRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageListResponse;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageTranslationRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LanguageTranslationResponse;
import lt.sciil.eLPA.ws.RetrofitModel.LicenseCheckRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LicenseCheckResponse;
import lt.sciil.eLPA.ws.RetrofitModel.LoadImageRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LoadImageResponse;
import lt.sciil.eLPA.ws.RetrofitModel.LoginRequest;
import lt.sciil.eLPA.ws.RetrofitModel.LoginResponse;
import lt.sciil.eLPA.ws.RetrofitModel.MachineResponse;
import lt.sciil.eLPA.ws.RetrofitModel.SystemSwitchRequest;
import lt.sciil.eLPA.ws.RetrofitModel.SystemSwitchResponse;
import lt.sciil.eLPA.ws.RetrofitModel.ModuleListRequest;
import lt.sciil.eLPA.ws.RetrofitModel.ModuleListResponse;
import lt.sciil.eLPA.ws.RetrofitModel.ModuleConfigurationVersionRequest;
import lt.sciil.eLPA.ws.RetrofitModel.ModuleConfigurationVersion;
import lt.sciil.eLPA.ws.RetrofitModel.SaveAuditRequest;
import lt.sciil.eLPA.ws.RetrofitModel.SaveAuditResponse;
import lt.sciil.eLPA.ws.RetrofitModel.SavePhotoRequest;
import lt.sciil.eLPA.ws.RetrofitModel.SavePhotoResponse;
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
