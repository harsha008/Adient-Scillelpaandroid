package lt.adient.mobility.eLPA.mvp;


public abstract class BasePresenter<V extends BaseView> {

    private V view;

    abstract public void onStart();

    abstract public void onStop();

    abstract public void onDestroy();

    public void attachView(V view) {
        this.view = view;
    }

    public void dettachView() {
        this.view = null;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public V getView() {
        return view;
    }

}
