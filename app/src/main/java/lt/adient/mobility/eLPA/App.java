package lt.adient.mobility.eLPA;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.greenrobot.eventbus.EventBus;

import io.fabric.sdk.android.Fabric;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import lt.adient.mobility.eLPA.dagger.AndroidModule;
import lt.adient.mobility.eLPA.dagger.ApplicationComponent;
import lt.adient.mobility.eLPA.dagger.DaggerApplicationComponent;
import lt.adient.mobility.eLPA.dagger.DatabaseModule;
import lt.adient.mobility.eLPA.dagger.NetModule;
import lt.adient.mobility.eLPA.events.ConnectivityChangeEvent;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.utils.Utility;

public class App extends Application {

    public static final String TAG = App.class.getName();
    public static final String SERVER_URL = "https://www.eLPA.lt:50273";
    private BroadcastReceiver broadcastReceiver;
    private static ApplicationComponent applicationComponent;

    private final String adientServerUrl = "https://elpa.ga.adient.com:50263";

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(getApplicationContext()))
                .databaseModule(new DatabaseModule())
                .netModule(new NetModule())
                .build();

        //Adient fixed url
        if (BuildConfig.FLAVOR.equals("adient_")) {
            applicationComponent.getPrefManager().setProfileServerUrl(adientServerUrl);
        }

        Fabric.with(this, new Crashlytics());

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Connectivity change");
                boolean isConnected = Utility.isNetworkAvailable(getApplicationContext());
                PrefManager prefManager = applicationComponent.getPrefManager();
                prefManager.setOffline(!isConnected);
                EventBus.getDefault().postSticky(new ConnectivityChangeEvent(isConnected));
            }
        };

        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(broadcastReceiver);
    }

    public static Scheduler getDefaultScheduler() {
        return Schedulers.computation();
    }

    public static Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
