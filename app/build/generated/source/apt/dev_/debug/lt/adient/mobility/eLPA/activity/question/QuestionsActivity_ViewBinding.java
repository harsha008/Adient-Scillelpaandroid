// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.activity.question;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import lt.adient.mobility.eLPA.adapter.CustomViewPager;

public class QuestionsActivity_ViewBinding implements Unbinder {
  private QuestionsActivity target;

  @UiThread
  public QuestionsActivity_ViewBinding(QuestionsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public QuestionsActivity_ViewBinding(QuestionsActivity target, View source) {
    this.target = target;

    target.questionNumber = Utils.findRequiredViewAsType(source, 2131689631, "field 'questionNumber'", TextView.class);
    target.answeredQuestions = Utils.findRequiredViewAsType(source, 2131689614, "field 'answeredQuestions'", TextView.class);
    target.lastQuestionButton = Utils.findRequiredViewAsType(source, 2131689633, "field 'lastQuestionButton'", Button.class);
    target.tittle = Utils.findRequiredViewAsType(source, 2131689613, "field 'tittle'", TextView.class);
    target.previousQuestionButton = Utils.findRequiredViewAsType(source, 2131689630, "field 'previousQuestionButton'", Button.class);
    target.toolbar = Utils.findRequiredViewAsType(source, 2131689612, "field 'toolbar'", Toolbar.class);
    target.nextQuestionButton = Utils.findRequiredViewAsType(source, 2131689632, "field 'nextQuestionButton'", Button.class);
    target.progressBar = Utils.findRequiredViewAsType(source, 2131689601, "field 'progressBar'", ProgressBar.class);
    target.firstQuestionButton = Utils.findRequiredViewAsType(source, 2131689629, "field 'firstQuestionButton'", Button.class);
    target.vpPager = Utils.findRequiredViewAsType(source, 2131689627, "field 'vpPager'", CustomViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QuestionsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.questionNumber = null;
    target.answeredQuestions = null;
    target.lastQuestionButton = null;
    target.tittle = null;
    target.previousQuestionButton = null;
    target.toolbar = null;
    target.nextQuestionButton = null;
    target.progressBar = null;
    target.firstQuestionButton = null;
    target.vpPager = null;
  }
}
