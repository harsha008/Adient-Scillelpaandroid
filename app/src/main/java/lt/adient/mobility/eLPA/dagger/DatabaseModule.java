package lt.adient.mobility.eLPA.dagger;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
import lt.adient.mobility.eLPA.utils.Translator;


@Module
public class DatabaseModule {
    public DatabaseModule() {
    }

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context applicationContext) {
        return OpenHelperManager.getHelper(applicationContext, DatabaseHelper.class);
    }

    @Provides
    @Singleton
    public AuditRepository provideAuditRepository(DatabaseHelper databaseHelper) {
        return new AuditRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public QuestionRepository provideQuestionRepository(DatabaseHelper databaseHelper) {
        return new QuestionRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public AnswerRepository provideAnswerRepository(DatabaseHelper databaseHelper) {
        return new AnswerRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public WorkstationRepository provideWorkstationRepository(DatabaseHelper databaseHelper) {
        return new WorkstationRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public ChapterRepository provideChapterRepository(DatabaseHelper databaseHelper) {
        return new ChapterRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository(DatabaseHelper databaseHelper) {
        return new UserRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public Translator provideTranslator(TranslationRepository translationRepository) {
        return new Translator(translationRepository);
    }

    @Provides
    @Singleton
    public TranslationRepository provideTranslationRepository(DatabaseHelper databaseHelper) {
        return new TranslationRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public ModuleRepository provideModuleRepository(DatabaseHelper databaseHelper) {
        return new ModuleRepository(databaseHelper);
    }

    @Provides
    @Singleton
    public LanguageRepository provideLanguageRepository(DatabaseHelper databaseHelper) {
        return new LanguageRepository(databaseHelper);
    }

}
