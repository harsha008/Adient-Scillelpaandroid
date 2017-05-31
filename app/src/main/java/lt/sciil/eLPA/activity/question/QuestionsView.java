package lt.sciil.eLPA.activity.question;

import android.support.annotation.StringRes;

import lt.sciil.eLPA.database.model.Answer;
import lt.sciil.eLPA.mvp.BaseView;

public interface QuestionsView extends BaseView {

    void setTittleText(String text, boolean isVisible);

    void showProgress(boolean show);

    void notifyAdapterDataChanged();

    void showToast(@StringRes int text, int length);

    void startSaveAuditService();

    void showFullScreenPhoto(String photoPath);

    void hideWorkstationPhoto();

    void selectQuestion(int questionPos);

    void finishActivity();

    void showSaveAlertDialog();

    void questionAnswered();

    void setSwipeable(boolean swipeable);

    void saveAnswer(Answer answer);

}
