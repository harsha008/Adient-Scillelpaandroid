// Generated code from Butter Knife. Do not modify!
package lt.adient.mobility.eLPA.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import uk.co.senab.photoview.PhotoView;

public class FullScreenDialog_ViewBinding implements Unbinder {
  private FullScreenDialog target;

  @UiThread
  public FullScreenDialog_ViewBinding(FullScreenDialog target, View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, 2131689674, "field 'imageView'", PhotoView.class);
    target.deletePictureButton = Utils.findRequiredViewAsType(source, 2131689677, "field 'deletePictureButton'", ImageButton.class);
    target.closeButton = Utils.findRequiredViewAsType(source, 2131689678, "field 'closeButton'", ImageButton.class);
    target.takePictureButton = Utils.findRequiredViewAsType(source, 2131689676, "field 'takePictureButton'", ImageButton.class);
    target.progressBar = Utils.findRequiredViewAsType(source, 2131689675, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FullScreenDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.deletePictureButton = null;
    target.closeButton = null;
    target.takePictureButton = null;
    target.progressBar = null;
  }
}
