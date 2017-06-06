package lt.adient.mobility.eLPA.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.database.AnswerRepository;
import lt.adient.mobility.eLPA.database.AuditRepository;
import lt.adient.mobility.eLPA.database.ChapterRepository;
import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.Chapter;
import lt.adient.mobility.eLPA.utils.ImageUtility;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.utils.Translator;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ResultCode;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SaveAuditRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SaveAuditResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SavePhotoRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.SavePhotoResponse;
import lt.adient.mobility.eLPA.ws.SciilAPI;

public class SaveAuditService extends Service {
    private File storageDir;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final int NOTIFICATION = R.string.local_service_started;
    private final int sendInterval = 10;
    @Inject
    SciilAPI sciilAPI;
    @Inject
    AuditRepository auditRepository;
    @Inject
    AnswerRepository answerRepository;
    @Inject
    ChapterRepository chapterRepository;
    @Inject
    PrefManager prefManager;
    @Inject
    NotificationManager notificationManager;
    @Inject
    Translator translator;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        App.getApplicationComponent().inject(this);
        storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String idUser = prefManager.getUserId();
        String idModule = prefManager.getModuleId();
        Long lgeId = prefManager.getLgeId();
        Disposable disposable = Observable.interval(0, sendInterval, TimeUnit.SECONDS)
                .flatMapIterable(offlineMode -> auditRepository.getNotSyncedAudits())
                .filter(audit -> {
                    boolean offlineMode = prefManager.isOffline();
                    if (offlineMode) {
                        updateNotification(translator.getTranslation(this, R.string.audits_will_be_saved_online_mode));
//                        EventBus.getDefault().post(new AuditUploadedEvent(audit));
                    }
                    return !offlineMode;
                })
                .map(audit -> {
                    Disposable answerDisposable = Observable.fromIterable(answerRepository.getNotSentPhotos())
                            .filter(answer -> answer.getDocId() != null && !answer.getDocId().equals(""))
                            .map(answer -> {
                                updateNotification(translator.getTranslation(this, R.string.uploading_photos));
                                String imageRef = answer.getAuditId() + answer.getQuestionId();
                                String path = storageDir.getPath() + "/" + answer.getDocId() + ".jpg";
                                String base64Image = ImageUtility.imageToBase64(path);
                                String name = answer.getDocId();
                                SavePhotoRequest savePhotoRequest = new SavePhotoRequest(idUser, idModule, lgeId, imageRef, base64Image, name);
                                return Pair.create(sciilAPI.savePhoto(savePhotoRequest).blockingFirst(), answer);
                            })
                            .filter(pair -> pair.first.getResultCode() == ResultCode.SUCCESSFUL)
                            .subscribeOn(App.getIOScheduler())
                            .observeOn(App.getDefaultScheduler())
                            .subscribe(pair -> saveImageResponse(pair.first, pair.second),
                                    throwable -> {
                                        Log.d("Service", throwable.getMessage(), throwable);
                                        Crashlytics.logException(throwable);
                                    },
                                    () -> {
                                        updateNotification(translator.getTranslation(this, R.string.uploading_audit));
                                        List<Answer> answers = answerRepository.getAnswersForAudit(audit.getAuditId());
                                        List<Chapter> chapters = chapterRepository.getChaptersForAudit(audit.getAuditId());
                                        SaveAuditRequest saveAuditRequest = new SaveAuditRequest(idUser, lgeId, idModule, audit.getAuditId(), chapters, answers);
                                        sciilAPI.saveAudit(saveAuditRequest)
                                                .subscribeOn(App.getDefaultScheduler())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(saveAuditResponse -> {
                                                    saveAuditResponse(saveAuditResponse, audit, answers);
//                                                    EventBus.getDefault().post(new AuditUploadedEvent(audit));
                                                    updateNotification(translator.getTranslation(this, R.string.finished_uploading));
                                                    stopForeground(true);
                                                    removeAllNotifications();
                                                }, throwable -> {
                                                    Log.d("Service", throwable.getMessage(), throwable);
                                                    Crashlytics.logException(throwable);
                                                });
                                    });
                    compositeDisposable.add(answerDisposable);
                    return audit;
                })
                .subscribeOn(App.getIOScheduler())
                .observeOn(App.getIOScheduler())
                .subscribe(audit -> {
                        },
                        throwable -> {
                            Log.d("Service", throwable.getMessage(), throwable);
                            Crashlytics.logException(throwable);
                        });
        compositeDisposable.add(disposable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private void saveImageResponse(SavePhotoResponse savePhotoResponse, Answer answer) {
        File oldName = new File(storageDir + "/" + answer.getDocId() + ".jpg");
        File newName = new File(storageDir + "/" + savePhotoResponse.getData().getDocId() + ".jpg");
        if (oldName.exists()) {
            oldName.renameTo(newName);
        }
        answer.setDocId(savePhotoResponse.getData().getDocId());
        answer.setPhotoSent(true);
        answerRepository.update(answer);
    }

    private void saveAuditResponse(SaveAuditResponse saveAuditResponse, Audit audit, List<Answer> answers) {
        if (saveAuditResponse.getResultCode() == ResultCode.SUCCESSFUL) {
            for (Answer answer : answers) {
                answer.setNeedSync(false);
                answerRepository.update(answer);
            }
            audit.setNeeedSync(false);
            auditRepository.update(audit);
        }
    }

    private void updateNotification(String updateText) {
        Notification notification = showNotification(updateText);
        notificationManager.notify(NOTIFICATION, notification);
    }

    private void removeAllNotifications() {
        notificationManager.cancelAll();
    }

    /**
     * Show a notification while this service is running.
     */
    private Notification showNotification(String text) {
        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_cloud_upload_black_24px)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(translator.getTranslation(this, R.string.save_audit_service))
                .setContentText(text)
                .build();
        return notification;
    }
}
