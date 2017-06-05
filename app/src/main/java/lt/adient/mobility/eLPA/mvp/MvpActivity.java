package lt.adient.mobility.eLPA.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.dagger.NonConfigurationComponent;

public abstract class MvpActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity {

    @Inject
    protected P presenter;
    private ComponentCache<NonConfigurationComponent> componentCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        componentCache = retrieveInjectorOrCreateNew();
        super.onCreate(savedInstanceState);
    }

    public NonConfigurationComponent getInjector() {
        return componentCache.getActivityComponent();
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().dettachView();
        getPresenter().onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @SuppressWarnings("unchecked")
    private ComponentCache<NonConfigurationComponent> retrieveInjectorOrCreateNew() {
        Object lastNonConfInstance = getLastCustomNonConfigurationInstance();
        if (lastNonConfInstance == null) {
            componentCache = new ComponentCache<>(App.getApplicationComponent().nonConfiguration());
            return componentCache;
        } else {
            return (ComponentCache<NonConfigurationComponent>) lastNonConfInstance;
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return componentCache;
    }

    public ComponentCache<NonConfigurationComponent> getComponentCache() {
        return componentCache;
    }
}
