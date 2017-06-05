// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterDialog_ViewBinding implements Unbinder {
  private FilterDialog target;

  @UiThread
  public FilterDialog_ViewBinding(FilterDialog target, View source) {
    this.target = target;

    target.toDateEditText = Utils.findRequiredViewAsType(source, 2131689654, "field 'toDateEditText'", EditText.class);
    target.fromDateEditText = Utils.findRequiredViewAsType(source, 2131689651, "field 'fromDateEditText'", EditText.class);
    target.clearUser = Utils.findRequiredViewAsType(source, 2131689647, "field 'clearUser'", ImageView.class);
    target.clearFromDate = Utils.findRequiredViewAsType(source, 2131689652, "field 'clearFromDate'", ImageView.class);
    target.filterButton = Utils.findRequiredViewAsType(source, 2131689656, "field 'filterButton'", Button.class);
    target.clearWorkstation = Utils.findRequiredViewAsType(source, 2131689649, "field 'clearWorkstation'", ImageView.class);
    target.workStationAutocomplete = Utils.findRequiredViewAsType(source, 2131689648, "field 'workStationAutocomplete'", AutoCompleteTextView.class);
    target.spinnerUser = Utils.findRequiredViewAsType(source, 2131689646, "field 'spinnerUser'", Spinner.class);
    target.toLabel = Utils.findRequiredViewAsType(source, 2131689653, "field 'toLabel'", TextView.class);
    target.workstationLabel = Utils.findRequiredViewAsType(source, 2131689620, "field 'workstationLabel'", TextView.class);
    target.userLabel = Utils.findRequiredViewAsType(source, 2131689618, "field 'userLabel'", TextView.class);
    target.clearToDate = Utils.findRequiredViewAsType(source, 2131689655, "field 'clearToDate'", ImageView.class);
    target.fromLabel = Utils.findRequiredViewAsType(source, 2131689650, "field 'fromLabel'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toDateEditText = null;
    target.fromDateEditText = null;
    target.clearUser = null;
    target.clearFromDate = null;
    target.filterButton = null;
    target.clearWorkstation = null;
    target.workStationAutocomplete = null;
    target.spinnerUser = null;
    target.toLabel = null;
    target.workstationLabel = null;
    target.userLabel = null;
    target.clearToDate = null;
    target.fromLabel = null;
  }
}
