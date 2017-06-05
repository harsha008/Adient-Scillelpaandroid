// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuestionFragmentMVP_ViewBinding implements Unbinder {
  private QuestionFragmentMVP target;

  @UiThread
  public QuestionFragmentMVP_ViewBinding(QuestionFragmentMVP target, View source) {
    this.target = target;

    target.okButtonLayout = Utils.findRequiredViewAsType(source, 2131689666, "field 'okButtonLayout'", LinearLayout.class);
    target.fixButtonLayout = Utils.findRequiredViewAsType(source, 2131689670, "field 'fixButtonLayout'", LinearLayout.class);
    target.fixButton = Utils.findRequiredViewAsType(source, 2131689672, "field 'fixButton'", Button.class);
    target.userProgressBar = Utils.findRequiredViewAsType(source, 2131689664, "field 'userProgressBar'", ProgressBar.class);
    target.fixButtonImage = Utils.findRequiredViewAsType(source, 2131689671, "field 'fixButtonImage'", ImageView.class);
    target.tittleLayout = Utils.findRequiredViewAsType(source, 2131689657, "field 'tittleLayout'", LinearLayout.class);
    target.questionTittle = Utils.findRequiredViewAsType(source, 2131689659, "field 'questionTittle'", TextView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, 2131689552, "field 'scrollView'", ScrollView.class);
    target.notOkButtonLayout = Utils.findRequiredViewAsType(source, 2131689668, "field 'notOkButtonLayout'", LinearLayout.class);
    target.questionPhoto = Utils.findRequiredViewAsType(source, 2131689661, "field 'questionPhoto'", ImageView.class);
    target.chapterTittle = Utils.findRequiredViewAsType(source, 2131689658, "field 'chapterTittle'", TextView.class);
    target.okButton = Utils.findRequiredViewAsType(source, 2131689667, "field 'okButton'", Button.class);
    target.questionEditText = Utils.findRequiredViewAsType(source, 2131689673, "field 'questionEditText'", EditText.class);
    target.questionProgressBar = Utils.findRequiredViewAsType(source, 2131689662, "field 'questionProgressBar'", ProgressBar.class);
    target.notOkButton = Utils.findRequiredViewAsType(source, 2131689669, "field 'notOkButton'", Button.class);
    target.expandArrow = Utils.findRequiredViewAsType(source, 2131689660, "field 'expandArrow'", ImageView.class);
    target.userPhoto = Utils.findRequiredViewAsType(source, 2131689663, "field 'userPhoto'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QuestionFragmentMVP target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.okButtonLayout = null;
    target.fixButtonLayout = null;
    target.fixButton = null;
    target.userProgressBar = null;
    target.fixButtonImage = null;
    target.tittleLayout = null;
    target.questionTittle = null;
    target.scrollView = null;
    target.notOkButtonLayout = null;
    target.questionPhoto = null;
    target.chapterTittle = null;
    target.okButton = null;
    target.questionEditText = null;
    target.questionProgressBar = null;
    target.notOkButton = null;
    target.expandArrow = null;
    target.userPhoto = null;
  }
}
