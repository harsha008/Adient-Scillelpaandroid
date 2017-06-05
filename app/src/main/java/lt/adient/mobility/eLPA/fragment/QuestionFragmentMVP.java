package lt.adient.mobility.eLPA.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.adient.mobility.eLPA.BuildConfig;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.activity.question.QuestionsView;
import lt.adient.mobility.eLPA.mvp.MvpFragment;
import lt.adient.mobility.eLPA.utils.AnimationUtility;
import lt.adient.mobility.eLPA.utils.ImageLoading;
import lt.adient.mobility.eLPA.utils.Translator;

import static lt.adient.mobility.eLPA.Constants.MAX_TEXTVIEW_LINES;

public class QuestionFragmentMVP extends MvpFragment<QuestionFragmentView, QuestionFragmentPresenter> implements ResettableScrollViewFragment, QuestionFragmentView {
    private static final String TAG = "QuestionFragment";

    private final static String QUESTION = "question";
    private final static String AUDIT = "audit";
    private static final String POSITION = "position";

    private static final int REQUEST_TAKE_PHOTO = 1;
    private Context context;
    private AnimationUtility animationUtility = AnimationUtility.getInstance();
    private QuestionsView questionsView;
    private ProgressDialog progressDialog;

    @BindView(R.id.questionProgressBar)
    ProgressBar questionProgressBar;
    @BindView(R.id.userProgressBar)
    ProgressBar userProgressBar;
    @BindView(R.id.userImage)
    ImageView userPhoto;
    @BindView(R.id.questionImage)
    ImageView questionPhoto;
    @BindView(R.id.ok_button)
    Button okButton;
    @BindView(R.id.fix_button)
    Button fixButton;
    @BindView(R.id.nok_button)
    Button notOkButton;
    @BindView(R.id.ok_button_layout)
    LinearLayout okButtonLayout;
    @BindView(R.id.fix_button_layout)
    LinearLayout fixButtonLayout;
    @BindView(R.id.nok_button_layout)
    LinearLayout notOkButtonLayout;
    @BindView(R.id.fix_button_image)
    ImageView fixButtonImage;
    @BindView(R.id.questionEditText)
    EditText questionEditText;
    @BindView(R.id.questionTittle)
    TextView questionTittle;
    @BindView(R.id.chaperTitle)
    TextView chapterTittle;
    @BindView(R.id.tittleLayout)
    LinearLayout tittleLayout;
    @BindView(R.id.expandArrow)
    ImageView expandArrow;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Inject
    Translator translator;
    @Inject
    ImageLoading imageLoading;

    public static QuestionFragmentMVP newInstance(int page, String question, String audit) {
        QuestionFragmentMVP fragmentQuestion = new QuestionFragmentMVP();
        Bundle args = new Bundle();
        args.putString(QUESTION, question);
        args.putString(AUDIT, audit);
        args.putInt(POSITION, page);
        fragmentQuestion.setArguments(args);
        return fragmentQuestion;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
        getPresenter().attachView(this);
        questionsView = (QuestionsView) getActivity();
        context = getContext();
        String questionId = getArguments().getString(QUESTION);
        Integer position = getArguments().getInt(POSITION);
        String auditId = getArguments().getString(AUDIT);
        getPresenter().setQuestionsView(questionsView);
        getPresenter().loadData(questionId, auditId);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, view);
        questionPhoto.setOnClickListener(v -> {
            getPresenter().questionPhotoClick();
        });
        userPhoto.setOnClickListener(v -> {
            getPresenter().userPhotoClick();
        });

        okButtonLayout.setOnClickListener(view12 -> {
            getPresenter().okButtonClick();
            questionsView.questionAnswered();
        });
        notOkButtonLayout.setOnClickListener(view1 -> {
            getPresenter().nokButtonClick();
            questionsView.questionAnswered();
        });

        fixButtonLayout.setOnClickListener(view1 -> {
            getPresenter().fixButtonClick();
        });

        tittleLayout.setOnClickListener(v -> {
            getPresenter().onTittleClick();
        });
        questionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (questionEditText.hasFocus()) {
                    getPresenter().setNeedSave(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getPresenter().restoreAnswerState();
        translate();
        return view;
    }

    @Override
    public void showFullScreenDialog(String imagePath, Boolean answerPhoto) {
        FullScreenDialog fullScreenDialog = FullScreenDialog.newInstance(imagePath, answerPhoto);
        fullScreenDialog.show(getFragmentManager(), "ImageViewFragment");
    }


    @Override
    public void animateInNokButton() {
        okButtonLayout.animate().alpha(0.0f).setDuration(150).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                okButtonLayout.setVisibility(View.GONE);
                fixButtonLayout.setVisibility(View.VISIBLE);
                questionEditText.setHintTextColor(ContextCompat.getColor(context, R.color.colorNotConnected));
            }
        });
    }

    @Override
    public void animateOutNokButton() {
        fixButtonLayout.animate().alpha(0.0f).setDuration(150).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fixButtonLayout.setVisibility(View.GONE);
                fixButtonImage.setImageResource(R.drawable.bandage_64px);
                fixButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
                okButtonLayout.setVisibility(View.VISIBLE);
                questionEditText.setHintTextColor(ContextCompat.getColor(context, R.color.colorPrimaryTextDark));
            }
        });
    }

    @Override
    public void setQuestionTittle(String text) {
        questionTittle.setText(text);
    }

    @Override
    public void setChapterTittle(String text) {
        chapterTittle.setText(text);
    }

    @Override
    public void toggleTittle(boolean expand) {
        animationUtility.addMaxLinesAnimation(chapterTittle, MAX_TEXTVIEW_LINES, expand);
        animationUtility.addMaxLinesAnimation(questionTittle, MAX_TEXTVIEW_LINES, expand);
        animationUtility.add180Rotation(expandArrow, expand);
        animationUtility.playTogether();
    }

    @Override
    public void setSwiping(boolean enabled) {
        questionsView.setSwipeable(enabled);
    }

    @Override
    public void setClickableOkButton(boolean clickable) {
        okButtonLayout.setClickable(clickable);
        okButtonLayout.setEnabled(clickable);
    }

    @Override
    public void setClickableNokButton(boolean clickable) {
        notOkButtonLayout.setClickable(clickable);
        notOkButtonLayout.setEnabled(clickable);
    }

    @Override
    public void checkIfTittleFits() {
        questionTittle.post(() -> {
            float textLength = questionTittle.getPaint().measureText(questionTittle.getText().toString());
            float viewWidth = questionTittle.getWidth();
            float chapterTextLength = chapterTittle.getPaint().measureText(chapterTittle.getText().toString());
            float chapterViewWidth = chapterTittle.getWidth();
            if (viewWidth >= textLength && chapterViewWidth >= chapterTextLength) {
                expandArrow.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = getPresenter().createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "Error while creating file for photo");
                Crashlytics.logException(ex);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                getPresenter().compressImageAsync(1, 80);
            }
        }
    }

    @Override
    public void showProgressDialog(boolean show) {
        if (show) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity(), R.style.CustomDialog);
                progressDialog.setTitle(translator.getTranslation(getActivity(), R.string.please_wait));
                progressDialog.setMessage(translator.getTranslation(getActivity(), R.string.saving_photo));
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    public void setUserProgressBarVisibility(boolean visibile) {
        userProgressBar.setVisibility(visibile ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setQuestionProgressBarVisibility(boolean visible) {
        questionProgressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void loadUserImage(String imagePath, @DrawableRes int errorDrawable) {
        imageLoading.loadImage(userPhoto, imagePath, errorDrawable, userProgressBar);
    }

    @Override
    public void loadUserImage(@DrawableRes int drawable) {
        imageLoading.loadImage(userPhoto, drawable, drawable, userProgressBar);
    }

    @Override
    public void loadQuestionImage(String imagePath, @DrawableRes int errorDrawable) {
        imageLoading.loadImage(questionPhoto, imagePath, errorDrawable, questionProgressBar);
    }

    private void translate() {
        translator.translate(okButton, notOkButton, fixButton, questionEditText);
    }

    @Override
    public void resetScrollView() {
        scrollView.scrollTo(0, 0);
    }

    @Override
    public String getQuestionCommentText() {
        return questionEditText.getText().toString();
    }

    @Override
    public void setAnswerInfoText(String text) {
        questionEditText.setText(text);
    }

    @Override
    public void changeOkColor(boolean active) {
        if (active) {
            okButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
            notOkButtonLayout.setVisibility(View.GONE);
        } else {
            okButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
            notOkButtonLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void changeNokColor(boolean active) {
        if (active) {
            notOkButton.setTextColor(ContextCompat.getColor(context, R.color.colorNotConnected));
        } else {
            notOkButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText));
        }

    }

    @Override
    public void setImmediatelyCorrected(boolean active) {
        if (active) {
            fixButtonImage.setImageResource(R.drawable.bandage_ok_64px);
            fixButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        } else {
            fixButtonImage.setImageResource(R.drawable.bandage_64px);
            fixButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        }
    }
}
