package lt.sciil.eLPA.dagger;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lt.sciil.eLPA.utils.ImageLoading;
import lt.sciil.eLPA.utils.PrefManager;
import lt.sciil.eLPA.ws.BaseUrlInterceptor;

import static android.content.Context.NOTIFICATION_SERVICE;

@Module
public class AndroidModule {

    private final Context applicationContext;

    public AndroidModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences(Context applicationContext) {
        return PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    @Provides
    @Singleton
    public PrefManager providePreferenceManager(SharedPreferences preferences, BaseUrlInterceptor interceptor) {
        return new PrefManager(preferences, interceptor);
    }

    @Provides
    @Singleton
    public Crashlytics provideCrashlytics() {
        return Crashlytics.getInstance();
    }

    @Provides
    @Named("storageDir")
    @Singleton
    public File provideExternalStorageDir(Context applicationContext) {
        return applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Provides
    @Singleton
    public NotificationManager provideNotificationManager(Context applicationContext) {
        return (NotificationManager) applicationContext.getSystemService(NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    public ImageLoading provideImageLoading(Context applicationContext) {
        Picasso.Builder builder = new Picasso.Builder(applicationContext);
//        builder.listener((picasso, uri, exception) -> {
//            exception.printStackTrace();
//        });
        Picasso picasso = builder.build();
//        picasso.setLoggingEnabled(true);
        return new ImageLoading(picasso);
    }
}
