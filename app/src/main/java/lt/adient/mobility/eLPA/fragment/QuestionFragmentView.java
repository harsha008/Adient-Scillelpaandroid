package lt.adient.mobility.eLPA.fragment;

import android.support.annotation.DrawableRes;

import lt.adient.mobility.eLPA.mvp.BaseView;

/**
 * Created by Simonas on 2017-02-10.
 */

public interface QuestionFragmentView extends BaseView {

    void checkIfTittleFits();

    void setClickableNokButton(boolean clickable);

    void showFullScreenDialog(String imagePath, Boolean answerPhoto);

    void animateInNokButton();

    void animateOutNokButton();

    void setQuestionTittle(String text);

    void setChapterTittle(String text);

    void toggleTittle(boolean expand);

    void setSwiping(boolean enabled);

    void setClickableOkButton(boolean clickable);

    void loadUserImage(String imagePath, @DrawableRes int errorDrawable);

    void loadUserImage(@DrawableRes int drawable);

    void dispatchTakePictureIntent();

    void showProgressDialog(boolean show);


    void setUserProgressBarVisibility(boolean visibile);

    void setQuestionProgressBarVisibility(boolean visible);

    void loadQuestionImage(String imagePath, @DrawableRes int errorDrawable);

    String getQuestionCommentText();

    void setAnswerInfoText(String text);

    void changeOkColor(boolean active);


    void changeNokColor(boolean active);

    void setImmediatelyCorrected(boolean active);
}
