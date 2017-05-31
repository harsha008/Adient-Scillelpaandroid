package lt.sciil.eLPA.fragment;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lt.sciil.eLPA.App;
import lt.sciil.eLPA.R;
import lt.sciil.eLPA.activity.question.QuestionsView;
import lt.sciil.eLPA.dagger.NonConfigurationScope;
import lt.sciil.eLPA.database.AnswerRepository;
import lt.sciil.eLPA.database.ChapterRepository;
import lt.sciil.eLPA.database.QuestionRepository;
import lt.sciil.eLPA.database.model.Answer;
import lt.sciil.eLPA.database.model.Chapter;
import lt.sciil.eLPA.database.model.PictureQuality;
import lt.sciil.eLPA.database.model.Question;
import lt.sciil.eLPA.events.DeletePictureEvent;
import lt.sciil.eLPA.events.ImageDownloadedEvent;
import lt.sciil.eLPA.events.SaveAnswerEvent;
import lt.sciil.eLPA.events.TakePictureEvent;
import lt.sciil.eLPA.mvp.BasePresenter;
import lt.sciil.eLPA.utils.ImageUtility;
import lt.sciil.eLPA.utils.JsonHelper;
import lt.sciil.eLPA.utils.PrefManager;

@NonConfigurationScope
public class QuestionFragmentPresenter extends BasePresenter<QuestionFragmentView> {

    private final PrefManager prefManager;
    private final File storageDir;
    private final ChapterRepository chapterRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    private Question question;
    private Answer answer;
    private boolean needSave = false;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String mCurrentPhotoPath;
    private String mCurrentPhotoName;
    private boolean tittleExpanded = false;
    private QuestionsView questionsView;

    @Inject
    public QuestionFragmentPresenter(PrefManager prefManager, @Named("storageDir") File storageDir,
                                     ChapterRepository chapterRepository,
                                     AnswerRepository answerRepository,
                                     QuestionRepository questionRepository) {
        this.prefManager = prefManager;
        this.storageDir = storageDir;
        this.chapterRepository = chapterRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void setQuestionsView(QuestionsView questionsView) {
        this.questionsView = questionsView;
    }

    public void loadData(String questionId, String auditId) {
        if (answer == null)
            answer = answerRepository.getAnswerForQuestion(questionId, auditId);
        if (question == null)
            question = questionRepository.getQuestionForAudit(questionId, auditId);
    }

    @Override
    public void onStart() {
        getView().checkIfTittleFits();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        if (needSave) {
            addAnswerToSaveList(getView().getQuestionCommentText());
        }
    }

    @Override
    public void onDestroy() {
        if (!compositeDisposable.isDisposed())
            compositeDisposable.clear();
    }

    @Subscribe
    public void onSaveAnswerEvent(SaveAnswerEvent event) {
        if (needSave) {
            addAnswerToSaveList(getView().getQuestionCommentText());
        }
    }


    @Subscribe
    public void onTakePictureEvent(TakePictureEvent event) {
        String thisFragmentAnswerPath = storageDir.getPath() + "/" + answer.getDocId() + ".jpg";
        if (event.getUserPhotoPath().equals(thisFragmentAnswerPath)) {
            getView().dispatchTakePictureIntent();
        }
    }

    @Subscribe
    public void onDeletePictureEvent(DeletePictureEvent event) {
        String thisFragmentAnswerPath = storageDir.getPath() + "/" + answer.getDocId() + ".jpg";
        if (event.getUserPhotoPath().equals(thisFragmentAnswerPath)) {
            answer.setDocId(thisFragmentAnswerPath);
            answer.setNeedSync(true);
            answer.setPhotoSent(true);
            answer.setDeletePhotoOnSave(true);
            getView().loadUserImage(R.drawable.camera_512px);
            needSave = true;
        }
    }

    @Subscribe
    public void onImageDownloaded(ImageDownloadedEvent event) {
        switch (event.getPhotoType()) {
            case Answer:
                String thisFragmentAnswerPath = storageDir.getPath() + "/" + answer.getDocId() + ".jpg";
                if (thisFragmentAnswerPath.equals(event.getImagePath())) {
                    getView().loadUserImage(thisFragmentAnswerPath, R.drawable.image_512px);
                }
                break;
            case Question:
                String thisFragmentQuestionPath = storageDir.getPath() + "/" + question.getDocId() + ".jpg";
                if (thisFragmentQuestionPath.equals(event.getImagePath())) {
                    getView().loadQuestionImage(thisFragmentQuestionPath, R.drawable.camera_512px);
                }
                break;
            default:
                break;
        }
    }

    public void compressImageAsync(final int sampleSize, final int quality) {
        getView().showProgressDialog(true);
        PictureQuality pictureQuality = prefManager.getPictureSizeSetting();
        Disposable disposable = Single.fromCallable(() -> ImageUtility.compressImage(mCurrentPhotoPath, sampleSize, quality, pictureQuality))
                .subscribeOn(App.getDefaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    answer.setDocId(mCurrentPhotoName);
                    answer.setPhotoSent(false);
                    answer.setDeletePhotoOnSave(false);
                    needSave = true;
                    mCurrentPhotoPath = null;
                    if (isViewAttached()) {
                        getView().setUserProgressBarVisibility(false);
                        getView().loadUserImage(file.getAbsolutePath(), R.drawable.camera_512px);
                        getView().showProgressDialog(false);
                    }
                }, throwable -> {
                    Crashlytics.logException(throwable);
                    Log.e("QuestionFragPresenter", throwable.getMessage(), throwable);
                    if (isViewAttached()) {
                        getView().showProgressDialog(false);
                    }
                });
        compositeDisposable.add(disposable);
    }


    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "user_photo_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        mCurrentPhotoName = image.getName().substring(0, image.getName().length() - 4);
        return image;
    }


    public void addAnswerToSaveList(String commentText) {
        String dateString = JsonHelper.formatPYPFormat(Calendar.getInstance().getTimeInMillis());
        String idUser = prefManager.getUserId();
        answer.setInfo(commentText);
        answer.setAnsweredDate(dateString);
        answer.setAnsweredByUserId(idUser);
        answer.setNeedSync(true);
        questionsView.saveAnswer(answer);
        needSave = false;
    }


    public void restoreAnswerState() {
        if (question != null && answer != null) {
            Chapter chapter = chapterRepository.getChapterById(question.getChapterId());
            getView().setQuestionTittle(question.getQuestionDesc());
            getView().setChapterTittle(chapter != null ? chapter.getChapterDesc() : "");
            getView().setAnswerInfoText(answer.getInfo());
            if (answer.getOk() == 1) {
                getView().changeOkColor(true);
            } else if (answer.getNotOk() == 1) {
                getView().changeNokColor(true);
                getView().animateInNokButton();
                if (answer.getImmediatelyCorrected() == 1) {
                    getView().setImmediatelyCorrected(true);
                }
            }
            String questionPhotoPath = storageDir + "/" + question.getDocId() + ".jpg";
            getView().loadQuestionImage(questionPhotoPath, R.drawable.image_512px);

            String userPhotoPath = storageDir + "/" + answer.getDocId() + ".jpg";
            getView().loadUserImage(userPhotoPath, R.drawable.camera_512px);
            tittleExpanded = false;
        }
    }

    public void setNeedSave(boolean save) {
        needSave = save;
        if (answer.getNotOk() == 1) {
            getView().setSwiping(!getView().getQuestionCommentText().isEmpty());
        }
    }

    public void onTittleClick() {
        tittleExpanded = !tittleExpanded;
        getView().toggleTittle(tittleExpanded);
    }

    public void questionPhotoClick() {
        String imagePath = storageDir.getPath() + "/" + question.getDocId() + ".jpg";
        getView().showFullScreenDialog(imagePath, false);
    }

    public void userPhotoClick() {
        if (answer.getDocId() == null || answer.getDocId().isEmpty() || answer.isDeletePhotoOnSave()) {
            getView().dispatchTakePictureIntent();
        } else {
            String imagePath = storageDir.getPath() + "/" + answer.getDocId() + ".jpg";
            getView().showFullScreenDialog(imagePath, true);
        }
    }

    public void okButtonClick() {
        boolean active = answer.getOk() == 0;
        getView().setClickableNokButton(!active);
        getView().changeOkColor(active);
        answer.setOk(active ? 1 : 0);
        addAnswerToSaveList(getView().getQuestionCommentText());
        getView().setSwiping(true);
    }

    public void nokButtonClick() {
        boolean active = answer.getNotOk() == 0;
        answer.setNotOk(active ? 1 : 0);
        getView().setClickableOkButton(!active);
        getView().changeNokColor(active);

        if (active) {
            getView().animateInNokButton();
            getView().setSwiping(!getView().getQuestionCommentText().isEmpty());
        } else {
            getView().animateOutNokButton();
            getView().setSwiping(true);
            answer.setImmediatelyCorrected(0);
        }
        addAnswerToSaveList(getView().getQuestionCommentText());
    }

    public void fixButtonClick() {
        boolean active = answer.getImmediatelyCorrected() == 0;
        answer.setImmediatelyCorrected(active ? 1 : 0);
        needSave = true;
        getView().setImmediatelyCorrected(active);
    }

}
