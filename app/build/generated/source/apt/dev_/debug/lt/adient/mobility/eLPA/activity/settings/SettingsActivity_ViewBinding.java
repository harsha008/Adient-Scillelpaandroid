// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.activity.settings;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingsActivity_ViewBinding implements Unbinder {
  private SettingsActivity target;

  @UiThread
  public SettingsActivity_ViewBinding(SettingsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingsActivity_ViewBinding(SettingsActivity target, View source) {
    this.target = target;

    target.profileLayout = Utils.findRequiredViewAsType(source, 2131689606, "field 'profileLayout'", LinearLayout.class);
    target.profileText = Utils.findRequiredViewAsType(source, 2131689607, "field 'profileText'", TextView.class);
    target.serverUrl = Utils.findRequiredViewAsType(source, 2131689604, "field 'serverUrl'", EditText.class);
    target.selectServer = Utils.findRequiredViewAsType(source, 2131689605, "field 'selectServer'", Button.class);
    target.profileAutocomplete = Utils.findRequiredViewAsType(source, 2131689608, "field 'profileAutocomplete'", AutoCompleteTextView.class);
    target.serverText = Utils.findRequiredViewAsType(source, 2131689603, "field 'serverText'", TextView.class);
    target.selectLanguageButton = Utils.findRequiredViewAsType(source, 2131689611, "field 'selectLanguageButton'", Button.class);
    target.languageSpinner = Utils.findRequiredViewAsType(source, 2131689610, "field 'languageSpinner'", Spinner.class);
    target.languageText = Utils.findRequiredViewAsType(source, 2131689609, "field 'languageText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profileLayout = null;
    target.profileText = null;
    target.serverUrl = null;
    target.selectServer = null;
    target.profileAutocomplete = null;
    target.serverText = null;
    target.selectLanguageButton = null;
    target.languageSpinner = null;
    target.languageText = null;
  }
}
