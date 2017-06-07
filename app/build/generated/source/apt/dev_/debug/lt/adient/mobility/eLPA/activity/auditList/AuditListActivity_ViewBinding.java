// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.activity.auditList;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuditListActivity_ViewBinding implements Unbinder {
  private AuditListActivity target;

  @UiThread
  public AuditListActivity_ViewBinding(AuditListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AuditListActivity_ViewBinding(AuditListActivity target, View source) {
    this.target = target;

    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, 2131689588, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.auditInfoText = Utils.findRequiredViewAsType(source, 2131689590, "field 'auditInfoText'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, 2131689612, "field 'toolbar'", Toolbar.class);
    target.auditRecyclerView = Utils.findRequiredViewAsType(source, 2131689589, "field 'auditRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AuditListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeRefreshLayout = null;
    target.auditInfoText = null;
    target.toolbar = null;
    target.auditRecyclerView = null;
  }
}
