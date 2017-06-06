// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package lt.adient.mobility.eLPA.dagger;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import java.io.File;
import javax.inject.Provider;
import lt.adient.mobility.eLPA.activity.auditList.AuditListActivity;
import lt.adient.mobility.eLPA.activity.auditList.AuditListActivity_MembersInjector;
import lt.adient.mobility.eLPA.activity.auditList.AuditListPresenter;
import lt.adient.mobility.eLPA.activity.auditList.AuditListPresenter_Factory;
import lt.adient.mobility.eLPA.activity.login.LoginActivity;
import lt.adient.mobility.eLPA.activity.login.LoginActivity_MembersInjector;
import lt.adient.mobility.eLPA.activity.login.LoginPresenter;
import lt.adient.mobility.eLPA.activity.login.LoginPresenter_Factory;
import lt.adient.mobility.eLPA.activity.question.QuestionsActivity;
import lt.adient.mobility.eLPA.activity.question.QuestionsActivity_MembersInjector;
import lt.adient.mobility.eLPA.activity.question.QuestionsPresenter;
import lt.adient.mobility.eLPA.activity.question.QuestionsPresenter_Factory;
import lt.adient.mobility.eLPA.activity.settings.SettingsActivity;
import lt.adient.mobility.eLPA.activity.settings.SettingsActivity_MembersInjector;
import lt.adient.mobility.eLPA.activity.settings.SettingsPresenter;
import lt.adient.mobility.eLPA.activity.settings.SettingsPresenter_Factory;
import lt.adient.mobility.eLPA.adapter.AuditListAdapter;
import lt.adient.mobility.eLPA.adapter.AuditListAdapter_MembersInjector;
import lt.adient.mobility.eLPA.database.AnswerRepository;
import lt.adient.mobility.eLPA.database.AuditRepository;
import lt.adient.mobility.eLPA.database.ChapterRepository;
import lt.adient.mobility.eLPA.database.DatabaseHelper;
import lt.adient.mobility.eLPA.database.LanguageRepository;
import lt.adient.mobility.eLPA.database.ModuleRepository;
import lt.adient.mobility.eLPA.database.QuestionRepository;
import lt.adient.mobility.eLPA.database.TranslationRepository;
import lt.adient.mobility.eLPA.database.UserRepository;
import lt.adient.mobility.eLPA.database.WorkstationRepository;
import lt.adient.mobility.eLPA.fragment.FilterDialog;
import lt.adient.mobility.eLPA.fragment.FilterDialog_MembersInjector;
import lt.adient.mobility.eLPA.fragment.FullScreenDialog;
import lt.adient.mobility.eLPA.fragment.FullScreenDialog_MembersInjector;
import lt.adient.mobility.eLPA.fragment.QuestionFragmentMVP;
import lt.adient.mobility.eLPA.fragment.QuestionFragmentMVP_MembersInjector;
import lt.adient.mobility.eLPA.fragment.QuestionFragmentPresenter;
import lt.adient.mobility.eLPA.fragment.QuestionFragmentPresenter_Factory;
import lt.adient.mobility.eLPA.service.SaveAuditService;
import lt.adient.mobility.eLPA.service.SaveAuditService_MembersInjector;
import lt.adient.mobility.eLPA.utils.ImageLoading;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.utils.Translator;
import lt.adient.mobility.eLPA.ws.BaseUrlInterceptor;
import lt.adient.mobility.eLPA.ws.SciilAPI;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class DaggerApplicationComponent implements ApplicationComponent {
  private Provider<Context> provideApplicationContextProvider;

  private Provider<DatabaseHelper> provideDatabaseHelperProvider;

  private Provider<TranslationRepository> provideTranslationRepositoryProvider;

  private Provider<Translator> provideTranslatorProvider;

  private MembersInjector<FullScreenDialog> fullScreenDialogMembersInjector;

  private Provider<UserRepository> provideUserRepositoryProvider;

  private Provider<WorkstationRepository> provideWorkstationRepositoryProvider;

  private Provider<SharedPreferences> providesSharedPreferencesProvider;

  private Provider<BaseUrlInterceptor> provideHostSelectionInterceptorProvider;

  private Provider<PrefManager> providePreferenceManagerProvider;

  private MembersInjector<FilterDialog> filterDialogMembersInjector;

  private Provider<QuestionRepository> provideQuestionRepositoryProvider;

  private MembersInjector<AuditListAdapter> auditListAdapterMembersInjector;

  private Provider<OkHttpClient> provideOkhttpClientProvider;

  private Provider<Gson> provideGsonProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<SciilAPI> provideSciilAPIProvider;

  private Provider<AuditRepository> provideAuditRepositoryProvider;

  private Provider<AnswerRepository> provideAnswerRepositoryProvider;

  private Provider<ChapterRepository> provideChapterRepositoryProvider;

  private Provider<NotificationManager> provideNotificationManagerProvider;

  private MembersInjector<SaveAuditService> saveAuditServiceMembersInjector;

  private Provider<ModuleRepository> provideModuleRepositoryProvider;

  private Provider<LanguageRepository> provideLanguageRepositoryProvider;

  private Provider<File> provideExternalStorageDirProvider;

  private Provider<ImageLoading> provideImageLoadingProvider;

  private DaggerApplicationComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.provideApplicationContextProvider =
        DoubleCheck.provider(
            AndroidModule_ProvideApplicationContextFactory.create(builder.androidModule));

    this.provideDatabaseHelperProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideDatabaseHelperFactory.create(
                builder.databaseModule, provideApplicationContextProvider));

    this.provideTranslationRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideTranslationRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideTranslatorProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideTranslatorFactory.create(
                builder.databaseModule, provideTranslationRepositoryProvider));

    this.fullScreenDialogMembersInjector =
        FullScreenDialog_MembersInjector.create(provideTranslatorProvider);

    this.provideUserRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideUserRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideWorkstationRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideWorkstationRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.providesSharedPreferencesProvider =
        DoubleCheck.provider(
            AndroidModule_ProvidesSharedPreferencesFactory.create(
                builder.androidModule, provideApplicationContextProvider));

    this.provideHostSelectionInterceptorProvider =
        DoubleCheck.provider(
            NetModule_ProvideHostSelectionInterceptorFactory.create(builder.netModule));

    this.providePreferenceManagerProvider =
        DoubleCheck.provider(
            AndroidModule_ProvidePreferenceManagerFactory.create(
                builder.androidModule,
                providesSharedPreferencesProvider,
                provideHostSelectionInterceptorProvider));

    this.filterDialogMembersInjector =
        FilterDialog_MembersInjector.create(
            provideTranslatorProvider,
            provideUserRepositoryProvider,
            provideWorkstationRepositoryProvider,
            providePreferenceManagerProvider);

    this.provideQuestionRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideQuestionRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.auditListAdapterMembersInjector =
        AuditListAdapter_MembersInjector.create(
            provideTranslatorProvider,
            provideUserRepositoryProvider,
            provideWorkstationRepositoryProvider,
            providePreferenceManagerProvider,
            provideQuestionRepositoryProvider);

    this.provideOkhttpClientProvider =
        DoubleCheck.provider(
            NetModule_ProvideOkhttpClientFactory.create(
                builder.netModule, provideHostSelectionInterceptorProvider));

    this.provideGsonProvider =
        DoubleCheck.provider(NetModule_ProvideGsonFactory.create(builder.netModule));

    this.provideRetrofitProvider =
        DoubleCheck.provider(
            NetModule_ProvideRetrofitFactory.create(
                builder.netModule, provideOkhttpClientProvider, provideGsonProvider));

    this.provideSciilAPIProvider =
        DoubleCheck.provider(
            NetModule_ProvideSciilAPIFactory.create(builder.netModule, provideRetrofitProvider));

    this.provideAuditRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideAuditRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideAnswerRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideAnswerRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideChapterRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideChapterRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideNotificationManagerProvider =
        DoubleCheck.provider(
            AndroidModule_ProvideNotificationManagerFactory.create(
                builder.androidModule, provideApplicationContextProvider));

    this.saveAuditServiceMembersInjector =
        SaveAuditService_MembersInjector.create(
            provideSciilAPIProvider,
            provideAuditRepositoryProvider,
            provideAnswerRepositoryProvider,
            provideChapterRepositoryProvider,
            providePreferenceManagerProvider,
            provideNotificationManagerProvider,
            provideTranslatorProvider);

    this.provideModuleRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideModuleRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideLanguageRepositoryProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideLanguageRepositoryFactory.create(
                builder.databaseModule, provideDatabaseHelperProvider));

    this.provideExternalStorageDirProvider =
        DoubleCheck.provider(
            AndroidModule_ProvideExternalStorageDirFactory.create(
                builder.androidModule, provideApplicationContextProvider));

    this.provideImageLoadingProvider =
        DoubleCheck.provider(
            AndroidModule_ProvideImageLoadingFactory.create(
                builder.androidModule, provideApplicationContextProvider));
  }

  @Override
  public void inject(FullScreenDialog fragment) {
    fullScreenDialogMembersInjector.injectMembers(fragment);
  }

  @Override
  public void inject(FilterDialog fragment) {
    filterDialogMembersInjector.injectMembers(fragment);
  }

  @Override
  public void inject(AuditListAdapter auditListAdapter) {
    auditListAdapterMembersInjector.injectMembers(auditListAdapter);
  }

  @Override
  public void inject(SaveAuditService saveAuditService) {
    saveAuditServiceMembersInjector.injectMembers(saveAuditService);
  }

  @Override
  public PrefManager getPrefManager() {
    return providePreferenceManagerProvider.get();
  }

  @Override
  public Context applicationContext() {
    return provideApplicationContextProvider.get();
  }

  @Override
  public NonConfigurationComponent nonConfiguration() {
    return new NonConfigurationComponentImpl();
  }

  public static final class Builder {
    private AndroidModule androidModule;

    private DatabaseModule databaseModule;

    private NetModule netModule;

    private Builder() {}

    public ApplicationComponent build() {
      if (androidModule == null) {
        throw new IllegalStateException(AndroidModule.class.getCanonicalName() + " must be set");
      }
      if (databaseModule == null) {
        this.databaseModule = new DatabaseModule();
      }
      if (netModule == null) {
        this.netModule = new NetModule();
      }
      return new DaggerApplicationComponent(this);
    }

    public Builder databaseModule(DatabaseModule databaseModule) {
      this.databaseModule = Preconditions.checkNotNull(databaseModule);
      return this;
    }

    public Builder androidModule(AndroidModule androidModule) {
      this.androidModule = Preconditions.checkNotNull(androidModule);
      return this;
    }

    public Builder netModule(NetModule netModule) {
      this.netModule = Preconditions.checkNotNull(netModule);
      return this;
    }
  }

  private final class NonConfigurationComponentImpl implements NonConfigurationComponent {
    private Provider<LoginPresenter> loginPresenterProvider;

    private MembersInjector<LoginActivity> loginActivityMembersInjector;

    private Provider<SettingsPresenter> settingsPresenterProvider;

    private MembersInjector<SettingsActivity> settingsActivityMembersInjector;

    private Provider<AuditListPresenter> auditListPresenterProvider;

    private MembersInjector<AuditListActivity> auditListActivityMembersInjector;

    private Provider<QuestionsPresenter> questionsPresenterProvider;

    private MembersInjector<QuestionsActivity> questionsActivityMembersInjector;

    private Provider<QuestionFragmentPresenter> questionFragmentPresenterProvider;

    private MembersInjector<QuestionFragmentMVP> questionFragmentMVPMembersInjector;

    private NonConfigurationComponentImpl() {
      initialize();
    }

    @SuppressWarnings("unchecked")
    private void initialize() {

      this.loginPresenterProvider =
          DoubleCheck.provider(
              LoginPresenter_Factory.create(
                  MembersInjectors.<LoginPresenter>noOp(),
                  DaggerApplicationComponent.this.providePreferenceManagerProvider,
                  DaggerApplicationComponent.this.provideSciilAPIProvider,
                  DaggerApplicationComponent.this.provideModuleRepositoryProvider,
                  DaggerApplicationComponent.this.provideUserRepositoryProvider,
                  DaggerApplicationComponent.this.provideWorkstationRepositoryProvider));

      this.loginActivityMembersInjector =
          LoginActivity_MembersInjector.create(
              loginPresenterProvider,
              DaggerApplicationComponent.this.provideTranslatorProvider,
              DaggerApplicationComponent.this.providePreferenceManagerProvider);

      this.settingsPresenterProvider =
          DoubleCheck.provider(
              SettingsPresenter_Factory.create(
                  MembersInjectors.<SettingsPresenter>noOp(),
                  DaggerApplicationComponent.this.provideModuleRepositoryProvider,
                  DaggerApplicationComponent.this.providePreferenceManagerProvider,
                  DaggerApplicationComponent.this.provideSciilAPIProvider,
                  DaggerApplicationComponent.this.provideTranslationRepositoryProvider,
                  DaggerApplicationComponent.this.provideLanguageRepositoryProvider));

      this.settingsActivityMembersInjector =
          SettingsActivity_MembersInjector.create(
              settingsPresenterProvider,
              DaggerApplicationComponent.this.provideTranslatorProvider,
              DaggerApplicationComponent.this.providePreferenceManagerProvider);

      this.auditListPresenterProvider =
          DoubleCheck.provider(
              AuditListPresenter_Factory.create(
                  MembersInjectors.<AuditListPresenter>noOp(),
                  DaggerApplicationComponent.this.providePreferenceManagerProvider,
                  DaggerApplicationComponent.this.provideAuditRepositoryProvider,
                  DaggerApplicationComponent.this.provideSciilAPIProvider));

      this.auditListActivityMembersInjector =
          AuditListActivity_MembersInjector.create(
              auditListPresenterProvider,
              DaggerApplicationComponent.this.provideTranslatorProvider,
              DaggerApplicationComponent.this.providePreferenceManagerProvider);

      this.questionsPresenterProvider =
          DoubleCheck.provider(
              QuestionsPresenter_Factory.create(
                  MembersInjectors.<QuestionsPresenter>noOp(),
                  DaggerApplicationComponent.this.provideAnswerRepositoryProvider,
                  DaggerApplicationComponent.this.provideAuditRepositoryProvider,
                  DaggerApplicationComponent.this.provideQuestionRepositoryProvider,
                  DaggerApplicationComponent.this.provideWorkstationRepositoryProvider,
                  DaggerApplicationComponent.this.provideChapterRepositoryProvider,
                  DaggerApplicationComponent.this.providePreferenceManagerProvider,
                  DaggerApplicationComponent.this.provideSciilAPIProvider));

      this.questionsActivityMembersInjector =
          QuestionsActivity_MembersInjector.create(
              questionsPresenterProvider,
              DaggerApplicationComponent.this.provideTranslatorProvider,
              DaggerApplicationComponent.this.provideExternalStorageDirProvider);

      this.questionFragmentPresenterProvider =
          DoubleCheck.provider(
              QuestionFragmentPresenter_Factory.create(
                  MembersInjectors.<QuestionFragmentPresenter>noOp(),
                  DaggerApplicationComponent.this.providePreferenceManagerProvider,
                  DaggerApplicationComponent.this.provideExternalStorageDirProvider,
                  DaggerApplicationComponent.this.provideChapterRepositoryProvider,
                  DaggerApplicationComponent.this.provideAnswerRepositoryProvider,
                  DaggerApplicationComponent.this.provideQuestionRepositoryProvider));

      this.questionFragmentMVPMembersInjector =
          QuestionFragmentMVP_MembersInjector.create(
              questionFragmentPresenterProvider,
              DaggerApplicationComponent.this.provideTranslatorProvider,
              DaggerApplicationComponent.this.provideImageLoadingProvider);
    }

    @Override
    public void inject(LoginActivity activity) {
      loginActivityMembersInjector.injectMembers(activity);
    }

    @Override
    public void inject(SettingsActivity activity) {
      settingsActivityMembersInjector.injectMembers(activity);
    }

    @Override
    public void inject(AuditListActivity activity) {
      auditListActivityMembersInjector.injectMembers(activity);
    }

    @Override
    public void inject(QuestionsActivity activity) {
      questionsActivityMembersInjector.injectMembers(activity);
    }

    @Override
    public void inject(QuestionFragmentMVP questionFragmentMVP) {
      questionFragmentMVPMembersInjector.injectMembers(questionFragmentMVP);
    }
  }
}