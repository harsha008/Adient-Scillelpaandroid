package lt.adient.mobility.eLPA.database;


import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.Audit;

public class AuditRepository {

    private final DatabaseHelper databaseHelper;
    private final Dao<Audit, String> auditDao;
    private static final String TAG = AuditRepository.class.getName();

    public AuditRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        auditDao = databaseHelper.getAuditDao();
    }

    public Audit getAuditById(String auditId) {
        try {
            return auditDao.queryForId(auditId);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public int update(Audit audit) {
        try {
            return auditDao.update(audit);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public List<Audit> getNotSyncedAudits() {
        try {
            return auditDao.queryForEq(Audit.AUDIT_NEED_SYNC, true);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public void createOrUpdate(Audit audit) {
        try {
            auditDao.createOrUpdate(audit);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public List<Audit> getAllAudits() {
        try {
            return auditDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }
}
