package lt.adient.mobility.eLPA.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.ws.BaseUrlInterceptor;
import lt.adient.mobility.eLPA.ws.SciilAPI;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    public NetModule() {
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        HttpUrl httpUrl = HttpUrl.parse(App.SERVER_URL);
        return new Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient(BaseUrlInterceptor interceptor) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    public BaseUrlInterceptor provideHostSelectionInterceptor() {
        return new BaseUrlInterceptor("");
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public SciilAPI provideSciilAPI(Retrofit retrofit) {
        return retrofit.create(SciilAPI.class);
    }

}
