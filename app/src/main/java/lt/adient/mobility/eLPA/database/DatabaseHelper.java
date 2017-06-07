package lt.adient.mobility.eLPA.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.Chapter;
import lt.adient.mobility.eLPA.database.model.Language;
import lt.adient.mobility.eLPA.database.model.ModuleData;
import lt.adient.mobility.eLPA.database.model.Question;
import lt.adient.mobility.eLPA.database.model.TranslatedValue;
import lt.adient.mobility.eLPA.database.model.User;
import lt.adient.mobility.eLPA.database.model.Workstation;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "auditApp.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 56;

    private Dao<Audit, String> auditDAO = null;
    private Dao<User, String> userDAO = null;
    private Dao<Workstation, String> workstationDAO = null;
    private Dao<Question, String> questionDAO = null;
    private Dao<Answer, String> answerDAO = null;
    private Dao<Chapter, String> chapterDAO = null;
    private Dao<TranslatedValue, String> translationDao = null;
    private Dao<ModuleData, String> moduleDao = null;
    private Dao<Language, Long> languageDao = null;

    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to createOrUpdate
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Audit.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Workstation.class);
            TableUtils.createTable(connectionSource, Question.class);
            TableUtils.createTable(connectionSource, Answer.class);
            TableUtils.createTable(connectionSource, Chapter.class);
            TableUtils.createTable(connectionSource, TranslatedValue.class);
            TableUtils.createTable(connectionSource, ModuleData.class);
            TableUtils.createTable(connectionSource, Language.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't createOrUpdate database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Audit.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Workstation.class, true);
            TableUtils.dropTable(connectionSource, Question.class, true);
            TableUtils.dropTable(connectionSource, Answer.class, true);
            TableUtils.dropTable(connectionSource, Chapter.class, true);
            TableUtils.dropTable(connectionSource, TranslatedValue.class, true);
            TableUtils.dropTable(connectionSource, ModuleData.class, true);
            TableUtils.dropTable(connectionSource, Language.class, true);
            // after we drop the old databases, we createOrUpdate the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will createOrUpdate it or just give the cached
     * value.
     */
    public Dao<Audit, String> getAuditDao() {
        if (auditDAO == null) {
            try {
                auditDAO = getDao(Audit.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return auditDAO;
    }

    public Dao<User, String> getUserDao() {
        if (userDAO == null) {
            try {
                userDAO = getDao(User.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return userDAO;
    }

    public Dao<Workstation, String> getWorkstationDao() {
        if (workstationDAO == null) {
            try {
                workstationDAO = getDao(Workstation.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return workstationDAO;
    }

    public Dao<Question, String> getQuestionDao() {
        if (questionDAO == null) {
            try {
                questionDAO = getDao(Question.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return questionDAO;
    }

    public Dao<Answer, String> getAnswerDao() {
        if (answerDAO == null) {
            try {
                answerDAO = getDao(Answer.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return answerDAO;
    }

    public Dao<Chapter, String> getChapterDao() {
        if (chapterDAO == null) {
            try {
                chapterDAO = getDao(Chapter.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return chapterDAO;
    }

    public Dao<TranslatedValue, String> getTranslationDao() {
        if (translationDao == null) {
            try {
                translationDao = getDao(TranslatedValue.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return translationDao;
    }

    public Dao<ModuleData, String> getModuleDao() {
        if (moduleDao == null) {
            try {
                moduleDao = getDao(ModuleData.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return moduleDao;
    }

    public Dao<Language, Long> getLanguageDao() {
        if (languageDao == null) {
            try {
                languageDao = getDao(Language.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return languageDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        auditDAO = null;
        userDAO = null;
        workstationDAO = null;
        questionDAO = null;
        answerDAO = null;
        chapterDAO = null;
        translationDao = null;
        moduleDao = null;
        languageDao = null;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        DataPersisterManager.registerDataPersisters(JsonObjectClassPersister.getSingleton());
    }
}