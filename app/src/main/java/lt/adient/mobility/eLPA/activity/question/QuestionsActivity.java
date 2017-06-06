package lt.adient.mobility.eLPA.activity.question;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.adapter.CustomViewPager;
import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.Question;
import lt.adient.mobility.eLPA.database.model.ViewToTranslate;
import lt.adient.mobility.eLPA.fragment.FullScreenDialog;
import lt.adient.mobility.eLPA.fragment.QuestionFragmentMVP;
import lt.adient.mobility.eLPA.fragment.ResettableScrollViewFragment;
import lt.adient.mobility.eLPA.mvp.MvpActivity;
import lt.adient.mobility.eLPA.service.SaveAuditService;
import lt.adient.mobility.eLPA.utils.Translator;

import static lt.adient.mobility.eLPA.utils.Utility.showTranslatedToast;

public class QuestionsActivity extends MvpActivity<QuestionsView, QuestionsPresenter> implements QuestionsView {

    private static final String TAG = QuestionsActivity.class.getName();
    @BindView(R.id.vpPager)
    protected CustomViewPager vpPager;
    @BindView(R.id.progressBarQuestions)
    protected ProgressBar progressBar;
    @BindView(R.id.firstQuestionButton)
    protected Button firstQuestionButton;
    @BindView(R.id.previousQuestionButton)
    protected Button previousQuestionButton;
    @BindView(R.id.questionNumber)
    protected TextView questionNumber;
    @BindView(R.id.nextQuestionButton)
    protected Button nextQuestionButton;
    @BindView(R.id.lastQuestionButton)
    protected Button lastQuestionButton;
    @BindView(R.id.answeredQuestions)
    protected TextView answeredQuestions;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.tittle)
    protected TextView tittle;

    private FragmentStatePagerAdapter adapterViewPager;
    private Menu menu;

    @Inject
    Translator translator;

    @Inject
    @Named("storageDir")
    File storageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        getInjector().inject(this);
        getPresenter().attachView(this);
        getPresenter().setStorageDir(storageDir);

        answeredQuestions.setVisibility(View.INVISIBLE);
        answeredQuestions.setOnClickListener(v -> getPresenter().selectFirstUnansweredQuestion());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.discard);
        toolbar.invalidate();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        adapterViewPager = new QuestionsPagerAdapter(getSupportFragmentManager(), getPresenter().getQuestions());
        vpPager.setAdapter(adapterViewPager);
        setupBottomNavigation();

        if (getIntent().getExtras() != null) {
            String auditId = getIntent().getExtras().getString(Audit.AUDIT_ID);
            getPresenter().loadAudit(auditId);
        }

        translateUI();
    }

    private void setupBottomNavigation() {
        firstQuestionButton.setOnClickListener(v -> selectFirstQuestion());
        previousQuestionButton.setOnClickListener(v -> selectQuestion(vpPager.getCurrentItem() - 1));
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                questionNumber.setText(String.valueOf(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        nextQuestionButton.setOnClickListener(v -> selectQuestion(vpPager.getCurrentItem() + 1));
        lastQuestionButton.setOnClickListener(v -> selectLastQuestion());
    }

    @Override
    public void selectQuestion(int position) {
        if (position >= 0 && position < adapterViewPager.getCount()) {
            if (vpPager.isPagingEnabled()) {
                vpPager.setCurrentItem(position, true);
            } else {
                showToast(R.string.not_all_answers_have_comment, Toast.LENGTH_SHORT);
            }
        }
    }

    private void selectFirstQuestion() {
        selectQuestion(0);
    }

    private void selectLastQuestion() {
        selectQuestion(adapterViewPager.getCount() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questions, menu);
        MenuItem actionSave = menu.findItem(R.id.action_save);
        MenuItem actionLocation = menu.findItem(R.id.action_location);
        translator.translate(new ViewToTranslate<>(actionSave, "save"));
        translator.translate(new ViewToTranslate<>(actionLocation, "location"));
        this.menu = menu;
        return true;
    }

    private void translateUI() {
        translator.translate(toolbar, tittle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                getPresenter().openWorkstationPhoto();
                return true;
            case R.id.action_save:
                getPresenter().saveAnswersAsync();
                return true;
            case android.R.id.home:
                getPresenter().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void hideWorkstationPhoto() {
        if (menu != null) {
            menu.findItem(R.id.action_location).setVisible(false);
        }
    }

    @Override
    public void setSwipeable(boolean swipeable) {
        vpPager.setPagingEnabled(swipeable);
    }

    @Override
    public void onBackPressed() {
        getPresenter().onBackPressed();
    }

    @Override
    public void showSaveAlertDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.CustomDialog)
                .setTitle(translator.getTranslation(this, R.string.leaving_audit))
                .setMessage(translator.getTranslation(this, R.string.save_changes))
                .setPositiveButton(translator.getTranslation(this, R.string.save), (dialog, which) -> getPresenter().saveAnswersAsync())
                .setNegativeButton(translator.getTranslation(this, R.string.cancel), (dialog, which) -> dialog.dismiss())
                .setNeutralButton(translator.getTranslation(this, R.string.do_not_save), (dialog, which) -> getPresenter().doNotSaveAndFinish())
                .create();
        alertDialog.show();
    }

    @Override
    public void setTittleText(String text, boolean isVisible) {
        answeredQuestions.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        answeredQuestions.setText(text);
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void notifyAdapterDataChanged() {
        adapterViewPager.notifyDataSetChanged();
    }

    @Override
    public void showToast(@StringRes int text, int length) {
        showTranslatedToast(getBaseContext(), text, length, translator);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    public static class QuestionsPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Question> questions;
        private ResettableScrollViewFragment lastItem;

        public QuestionsPagerAdapter(FragmentManager fragmentManager, List<Question> questions) {
            super(fragmentManager);
            this.questions = questions;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return questions.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return QuestionFragmentMVP.newInstance(position, questions.get(position).getQuestionId(), questions.get(position).getAuditId());
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);

            if (lastItem != null) {
                lastItem.resetScrollView();
                lastItem = null;
            }
            if (object instanceof ResettableScrollViewFragment) {
                lastItem = (ResettableScrollViewFragment) object;
            }
        }
    }

    @Override
    public void showFullScreenPhoto(String photoPath) {
        FullScreenDialog fullScreenDialog = FullScreenDialog.newInstance(photoPath, false);
        fullScreenDialog.show(getSupportFragmentManager(), "ImageViewFragment");
    }

    @Override
    public void startSaveAuditService() {
        Intent i = new Intent(getBaseContext(), SaveAuditService.class);
        startService(i);
        finish();
    }

    @Override
    public void questionAnswered() {
        getPresenter().updateTittle();
    }

    @Override
    public void saveAnswer(Answer answer) {
        getPresenter().addAnswerToSaveList(answer);
    }

}
