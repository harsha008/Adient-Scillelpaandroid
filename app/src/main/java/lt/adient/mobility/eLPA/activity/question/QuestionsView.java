package lt.adient.mobility.eLPA.activity.question;

import android.support.annotation.StringRes;

import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.mvp.BaseView;

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
