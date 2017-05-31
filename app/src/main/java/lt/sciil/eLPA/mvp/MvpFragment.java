package lt.sciil.eLPA.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import java.util.UUID;

import javax.inject.Inject;

import lt.sciil.eLPA.App;
import lt.sciil.eLPA.dagger.NonConfigurationComponent;


public abstract class MvpFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment {

    @Inject
    protected P presenter;
    private static final String UUID_KEY = "UUID_KEY";
    private ComponentCache<NonConfigurationComponent> componentCache;
    private UUID uuid;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MvpActivity) {
            componentCache = ((MvpActivity) context).getComponentCache();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            uuid = (UUID) savedInstanceState.getSerializable(UUID_KEY);
    }

    public NonConfigurationComponent getInjector() {
        NonConfigurationComponent component = componentCache.getFragmentComponent(uuid);
        if (component == null) {
            component = App.getApplicationComponent().nonConfiguration();
            uuid = componentCache.putFragmentComponent(component);
        }
        return component;
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        getPresenter().dettachView();
        if (!getActivity().isChangingConfigurations() && !retainPresenter()) {
            componentCache.removeComponent(uuid);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(UUID_KEY, uuid);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        componentCache = null;
    }

    public boolean retainPresenter() {
        return true;
    }
}
