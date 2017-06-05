package lt.adient.mobility.eLPA.database;


import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.Chapter;

public class ChapterRepository {

    private final DatabaseHelper databaseHelper;
    private final Dao<Chapter, String> chapterDao;
    private static final String TAG = ChapterRepository.class.getName();

    public ChapterRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        chapterDao = databaseHelper.getChapterDao();
    }

    public Chapter getChapterById(String id) {
        try {
            return chapterDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public void clearAllChapters() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Chapter.class);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Chapter chapter) {
        try {
            return chapterDao.createOrUpdate(chapter);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public List<Chapter> getChaptersForAudit(String auditId) {
        try {
            return chapterDao.queryForEq(Audit.AUDIT_ID, auditId);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

}
