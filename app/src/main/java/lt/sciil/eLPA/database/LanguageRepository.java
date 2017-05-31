package lt.sciil.eLPA.database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.sciil.eLPA.database.model.Language;

public class LanguageRepository {

    private final DatabaseHelper databaseHelper;
    private final Dao<Language, Long> languageDao;
    private static final String TAG = QuestionRepository.class.getSimpleName();

    public LanguageRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        this.languageDao = databaseHelper.getLanguageDao();
    }


    public Dao.CreateOrUpdateStatus createOrUpdate(Language language) {
        try {
            return languageDao.createOrUpdate(language);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public Integer create(List<Language> languageList) {
        try {
            return languageDao.create(languageList);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public List<Language> getAll() {
        try {
            return languageDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public boolean isEmpty() {
        try {
            PreparedQuery<Language> query = languageDao
                    .queryBuilder()
                    .setCountOf(true)
                    .prepare();
            long count = languageDao.countOf(query);
            return count == 0;
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return true;
    }

    public void deleteTable() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Language.class);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

}
