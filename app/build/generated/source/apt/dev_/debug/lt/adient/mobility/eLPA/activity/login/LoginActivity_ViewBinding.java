// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.activity.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131689596;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.connectedCheckbox = Utils.findRequiredViewAsType(source, 2131689598, "field 'connectedCheckbox'", ImageView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, 2131689612, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, 2131689596, "field 'loginButton' and method 'login'");
    target.loginButton = Utils.castView(view, 2131689596, "field 'loginButton'", Button.class);
    view2131689596 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.login(p0);
      }
    });
    target.rememberCheckbox = Utils.findRequiredViewAsType(source, 2131689597, "field 'rememberCheckbox'", CheckBox.class);
    target.userEditText = Utils.findRequiredViewAsType(source, 2131689593, "field 'userEditText'", EditText.class);
    target.passwordEditText = Utils.findRequiredViewAsType(source, 2131689592, "field 'passwordEditText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.connectedCheckbox = null;
    target.toolbar = null;
    target.loginButton = null;
    target.rememberCheckbox = null;
    target.userEditText = null;
    target.passwordEditText = null;

    view2131689596.setOnClickListener(null);
    view2131689596 = null;
  }
}
