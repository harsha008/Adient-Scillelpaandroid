package lt.adient.mobility.eLPA.activity.question;

import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.dagger.NonConfigurationScope;
import lt.adient.mobility.eLPA.database.AnswerRepository;
import lt.adient.mobility.eLPA.database.AuditRepository;
import lt.adient.mobility.eLPA.database.ChapterRepository;
import lt.adient.mobility.eLPA.database.QuestionRepository;
import lt.adient.mobility.eLPA.database.WorkstationRepository;
import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.AuditStatus;
import lt.adient.mobility.eLPA.database.model.Chapter;
import lt.adient.mobility.eLPA.database.model.Question;
import lt.adient.mobility.eLPA.database.model.Workstation;
import lt.adient.mobility.eLPA.events.ImageDownloadedEvent;
import lt.adient.mobility.eLPA.events.PhotoType;
import lt.adient.mobility.eLPA.events.SaveAnswerEvent;
import lt.adient.mobility.eLPA.mvp.BasePresenter;
import lt.adient.mobility.eLPA.utils.ImageUtility;
import lt.adient.mobility.eLPA.utils.JsonHelper;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditQuestionRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditQuestionResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditResetBody;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoadImageRequest;
import lt.adient.mobility.eLPA.ws.RetrofitModel.LoadImageResponse;
import lt.adient.mobility.eLPA.ws.RetrofitModel.ResultCode;
import lt.adient.mobility.eLPA.ws.SciilAPI;

@NonConfigurationScope
public class QuestionsPresenter extends BasePresenter<QuestionsView> {

    private final String TAG = QuestionsPresenter.class.getSimpleName();
    private final List<Question> questions = new ArrayList<>();
    private Subject<Boolean> tittleUpdater = PublishSubject.create();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Workstation workstation;
    private final List<Answer> answersToSave = new ArrayList<>();
    private Audit audit;

    private final AnswerRepository answerRepository;
    private final AuditRepository auditRepository;
    private final QuestionRepository questionRepository;
    private final WorkstationRepository workstationRepository;
    private final ChapterRepository chapterRepository;
    private final PrefManager prefManager;
    private final SciilAPI sciilAPI;
    private File storageDir;

    @Inject
    public QuestionsPresenter(AnswerRepository answerRepository,
                              AuditRepository auditRepository,
                              QuestionRepository questionRepository,
                              WorkstationRepository workstationRepository,
                              ChapterRepository chapterRepository,
                              PrefManager prefManager, SciilAPI sciilAPI) {
        this.answerRepository = answerRepository;
        this.auditRepository = auditRepository;
        this.questionRepository = questionRepository;
        this.workstationRepository = workstationRepository;
        this.chapterRepository = chapterRepository;
        this.prefManager = prefManager;
        this.sciilAPI = sciilAPI;
    }

    void loadAudit(String auditId) {
        audit = auditRepository.getAuditById(auditId);
        if (audit == null) {
            getView().showToast(R.string.could_not_load_audit, Toast.LENGTH_LONG);
            getView().finishActivity();
            return;
        }
        createTittleUpdater();
        boolean offlineMode = prefManager.isOffline();
        if (offlineMode) {
            loadQuestionsOffline();
        } else {
            downloadQuestions();
            downloadWorkStationPhoto();
        }
    }

    void updateTittle() {
        tittleUpdater.onNext(true);
    }

    private void createTittleUpdater() {
        Disposable disposable = tittleUpdater
                .map(aBoolean -> getAnsweredCount())
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> {
                            getView().setTittleText(count + "/" + questions.size(), true);
                        },
                        throwable -> Log.e(TAG, throwable.getMessage(), throwable));
        compositeDisposable.add(disposable);
    }

    private void downloadQuestions() {
        String idUser = prefManager.getUserId();
        String idModule = prefManager.getModuleId();
        Long lgeId = prefManager.getLgeId();
        AuditQuestionRequest auditQuestionRequest = new AuditQuestionRequest(audit.getAuditId(), idModule, idUser, lgeId);
        Disposable disposable = sciilAPI.getAuditQuestions(auditQuestionRequest)
                .flatMapIterable(auditQuestionResponse -> auditQuestionResponse.getResult1().getAuditQuestions())
                .flatMapIterable(auditQuestion -> {
                    Chapter chapter = auditQuestion.getChapter();
                    chapter.setAuditId(audit.getAuditId());
                    chapterRepository.createOrUpdate(chapter);
                    for (AuditQuestionResponse.AuditQuestionResponseItem.AuditQuestion.QuestionItem questionItem : auditQuestion.getQuestions()) {
                        questionItem.getQuestion().setChapterId(chapter.getChapterId());
                        questionItem.getAnswer().setChapterId(chapter.getChapterId());
                    }
                    return auditQuestion.getQuestions();
                })
                .map(questionItem -> {
                    Question question = questionItem.getQuestion();
                    question.setAuditId(audit.getAuditId());
                    questionRepository.createOrUpdate(question);
                    Answer answer = questionItem.getAnswer();
                    answer.setQuestionId(question.getQuestionId());
                    answer.setAuditId(audit.getAuditId());
                    List<Answer> notSyncedAnswers = answerRepository.getNotSyncedAnswers(audit.getAuditId());
                    if (!notSyncedAnswers.contains(answer))
                        answerRepository.createOrUpdate(answer);
                    return question;
                })
                .toList()
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(questionList -> {
                    clearAndAddNewQuestions(questionList);
                    updateTittle();
                    getView().showProgress(false);
                    selectFirstUnansweredQuestion();
                    downloadPhotos();
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage(), throwable);
                });

        compositeDisposable.add(disposable);
    }

    private void loadQuestionsOffline() {
        Disposable disposable = Observable.fromCallable(() -> questionRepository.getAllQuestionsForAudit(audit.getAuditId()))
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(questionList -> {
                    clearAndAddNewQuestions(questionList);
                    updateTittle();
                    getView().showProgress(false);
                    selectFirstUnansweredQuestion();
                });
        compositeDisposable.add(disposable);
    }

    private void clearAndAddNewQuestions(List<Question> questionList) {
        questions.clear();
        questions.addAll(questionList);
        getView().notifyAdapterDataChanged();
    }

    private void downloadPhotos() {
        Disposable answerDisposable = Observable.fromIterable(answerRepository.getAnswersForAudit(audit.getAuditId()))
                .map(Answer::getDocId)
                .filter(docId -> !docId.isEmpty())
                .filter(this::checkIfNeedToDownload)
                .flatMap(this::downloadPhoto)
                .map(this::decodeAndSaveImage)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            EventBus.getDefault().post(new ImageDownloadedEvent(s, PhotoType.Answer));
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage(), throwable);
                            getView().showToast(R.string.server_problem, Toast.LENGTH_LONG);

                        });
        Disposable questionDisposable = Observable.fromIterable(questions)
                .map(Question::getDocId)
                .filter(docId -> !docId.isEmpty())
                .filter(this::checkIfNeedToDownload)
                .flatMap(this::downloadPhoto)
                .map(this::decodeAndSaveImage)
                .subscribeOn(App.getIOScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            EventBus.getDefault().post(new ImageDownloadedEvent(s, PhotoType.Question));
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage(), throwable);
                            getView().showToast(R.string.server_problem, Toast.LENGTH_LONG);
                        });
        compositeDisposable.add(answerDisposable);
        compositeDisposable.add(questionDisposable);
    }

    private boolean checkIfNeedToDownload(String docId) {
        File image = new File(storageDir.getPath() + "/" + docId + ".jpg");
        return !image.exists();
    }

    private Observable<LoadImageResponse> downloadPhoto(String docId) {
        String idUser = prefManager.getUserId();
        String idModule = prefManager.getModuleId();
        Long lgeId = prefManager.getLgeId();
        LoadImageRequest loadImageRequest = new LoadImageRequest(docId, idUser, lgeId, idModule);
        return sciilAPI.getPhoto(loadImageRequest);
    }

    void saveAnswersAsync() {
        EventBus.getDefault().post(new SaveAnswerEvent());
        for (Answer answer : getAllAnswers()) {
            if (answer.getNotOk() == 1 && answer.getInfo().isEmpty()) {
                getView().showToast(R.string.not_all_answers_have_comment, Toast.LENGTH_SHORT);
                return;
            }
        }
        Disposable disposable = Observable.fromIterable(answersToSave)
                .doOnNext(answer -> {
                    if (answer.isDeletePhotoOnSave()) {
                        new File(answer.getDocId()).delete();
                        answer.setDocId("");
                    }
                    answerRepository.update(answer);
                })
                .doOnComplete(this::saveAudit)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(answer -> {
                }, throwable -> {
                    Log.e(TAG, "Exception", throwable);
                }, () -> {
                    getView().startSaveAuditService();
                });
        compositeDisposable.add(disposable);
    }

    private void saveAudit() {
        String idUser = prefManager.getUserId();
        String dateString = JsonHelper.formatPYPFormat(Calendar.getInstance().getTimeInMillis());
        audit.setNeeedSync(true);
        if (audit.getStartedDate() == null) {
            audit.setStartedDate(dateString);
            audit.setAuditStatus(AuditStatus.Started);
            audit.setStartedByUserId(idUser);
        }
        Integer result = auditRepository.update(audit);
    }

    void openWorkstationPhoto() {
        Workstation workstation = getWorkstation();
        String path = storageDir + "/" + workstation.getDocId() + ".jpg";
        getView().showFullScreenPhoto(path);
    }

    private Workstation getWorkstation() {
        if (workstation != null)
            return workstation;
        workstation = workstationRepository.getWorkstationById(audit.getMachineId());
        return workstation;
    }

    private void downloadWorkStationPhoto() {
        String idUser = prefManager.getUserId();
        String idModule = prefManager.getModuleId();
        Long lgeId = prefManager.getLgeId();
        Disposable disposable = Observable.fromCallable(this::getWorkstation)
                .map(Workstation::getDocId)
                .filter(docId -> !docId.isEmpty())
                .filter(this::checkIfNeedToDownload)
                .flatMap(docId -> {
                    LoadImageRequest loadImageRequest = new LoadImageRequest(docId, idUser, lgeId, idModule);
                    return sciilAPI.getPhoto(loadImageRequest);
                })
                .map(this::decodeAndSaveImage)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        docId -> {
                            if (docId.isEmpty()) {
                                getView().hideWorkstationPhoto();
                            } else {
                                EventBus.getDefault().post(new ImageDownloadedEvent(docId, PhotoType.Workstation));
                            }
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage(), throwable);
                            getView().showToast(R.string.server_problem, Toast.LENGTH_LONG);
                        });
        compositeDisposable.add(disposable);
    }

    void selectFirstUnansweredQuestion() {
        Disposable disposable = Observable
                .fromIterable(getAllAnswers())
                .filter(answer -> answer.getOk() == 0 && answer.getNotOk() == 0)
                .map(this::getQuestionFromAnswer)
                .map(questions::indexOf)
                .reduce((currentMin, number) -> number < currentMin ? number : currentMin)
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getView()::selectQuestion, throwable -> Log.e(TAG, throwable.getMessage(), throwable));
        compositeDisposable.add(disposable);
    }

    private Question getQuestionFromAnswer(Answer answer) {
        for (Question question : questions) {
            if (question.getQuestionId().equals(answer.getQuestionId()))
                return question;
        }
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        if (!compositeDisposable.isDisposed())
            compositeDisposable.clear();
    }


    private String decodeAndSaveImage(LoadImageResponse loadImageResponse) throws IOException {
        if (loadImageResponse.getResultCode() == ResultCode.SUCCESSFUL && loadImageResponse.getData() != null) {
            String base64Image = loadImageResponse.getData().getAsBase64();
            String imageId = loadImageResponse.getData().getDocId();
            String imagePath = storageDir + "/" + imageId + ".jpg";
            return ImageUtility.decodeAndSaveImage(imagePath, base64Image);
        }
        return "";
    }

    void doNotSaveAndFinish() {
        if (audit.getStartedDate() == null) {
            AuditResetBody auditResetBody = new AuditResetBody(audit.getAuditId(), prefManager.getModuleRequest(), prefManager.getUserRequest());
            sciilAPI.resetAuditStatus(auditResetBody)
                    .subscribeOn(App.getDefaultScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(auditResetResponse -> {
                        Log.d("Reset audit", auditResetResponse.getResultCode().toString());
                    }, throwable -> {
                        Log.d("Reset audit", throwable.getMessage(), throwable);
                        Crashlytics.logException(throwable);
                    });
        }
        getView().finishActivity();
    }

    void addAnswerToSaveList(Answer answer) {
        if (!answersToSave.contains(answer)) {
            answersToSave.add(answer);
        } else {
            answersToSave.remove(answer);
            answersToSave.add(answer);
        }
    }

    private List<Answer> getAllAnswers() {
        List<Answer> fromDb = answerRepository.getAnswersForAudit(audit.getAuditId());
        List<Answer> all = new ArrayList<>();
        all.addAll(answersToSave);
        for (Answer answer : fromDb) {
            if (!all.contains(answer)) {
                all.add(answer);
            }
        }
        return all;
    }

    private Long getAnsweredCount() {
        return Observable.fromIterable(getAllAnswers())
                .filter(answer -> answer.getOk() == 1 || answer.getNotOk() == 1)
                .count()
                .blockingGet();
    }

    List<Question> getQuestions() {
        return questions;
    }

    void setStorageDir(File storageDir) {
        this.storageDir = storageDir;
    }

    void onBackPressed() {
        EventBus.getDefault().post(new SaveAnswerEvent());
        if (answersToSave.isEmpty()) {
            doNotSaveAndFinish();
            return;
        }
        getView().showSaveAlertDialog();
    }
}
