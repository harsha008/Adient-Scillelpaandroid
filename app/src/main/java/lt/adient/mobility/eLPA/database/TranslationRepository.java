package lt.adient.mobility.eLPA.database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.TranslatedValue;


public class TranslationRepository {

    private final Dao<TranslatedValue, String> translationDao;
    private static final String TAG = TranslationRepository.class.getName();

    public TranslationRepository(DatabaseHelper databaseHelper) {
        translationDao = databaseHelper.getTranslationDao();
    }

    public void createOrUpdate(TranslatedValue translatedValue) {
        try {
            translationDao.createOrUpdate(translatedValue);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public List<TranslatedValue> getAll() {
        try {
            return translationDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }
}
