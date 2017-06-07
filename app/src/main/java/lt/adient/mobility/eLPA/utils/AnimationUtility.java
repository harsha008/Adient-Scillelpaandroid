package lt.adient.mobility.eLPA.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnimationUtility {

    private static AnimationUtility instance;
    private final AnimatorSet animatorSet;
    private List<Animator> animators = new ArrayList<>();

    public static AnimationUtility getInstance() {
        if (instance == null) {
            instance = new AnimationUtility();
        }
        return instance;
    }

    private AnimationUtility() {
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void addMaxLinesAnimation(TextView view, int minLines, boolean expand) {
        int startHeight = view.getMeasuredHeight();
        view.setMaxLines(expand ? Integer.MAX_VALUE : minLines);
        view.measure(
                View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int endHeight = view.getMeasuredHeight();

        ValueAnimator slideAnimation = ValueAnimator.ofInt(startHeight, endHeight).setDuration(200);
        slideAnimation.addUpdateListener(animation -> {
            view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
            view.requestLayout();
        });
        slideAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setHeight(startHeight);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setMaxLines(expand ? Integer.MAX_VALUE : minLines);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animators.add(slideAnimation);
    }

    public void add180Rotation(View view, boolean expand) {
        float startRotation = expand ? 0 : 180;
        float endRotation = expand ? 180 : 0;
        ValueAnimator slideAnimation = ValueAnimator.ofFloat(startRotation, endRotation).setDuration(200);
        slideAnimation.addUpdateListener(animation -> {
            view.setRotation((Float) animation.getAnimatedValue());
            view.requestLayout();
        });
        animators.add(slideAnimation);
    }

    public void playTogether() {
        animatorSet.playTogether(animators);
        animatorSet.start();
        animators.clear();
    }

    public void stopAnimations() {
        animatorSet.end();
        animators.clear();
    }

    public void playSequentially() {
        animatorSet.playSequentially(animators);
        animatorSet.start();
        animators.clear();
    }
}
