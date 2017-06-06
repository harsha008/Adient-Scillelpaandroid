package lt.adient.mobility.eLPA.database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.Workstation;

public class WorkstationRepository {

    private final DatabaseHelper databaseHelper;
    private final Dao<Workstation, String> workstationDao;
    private static final String TAG = WorkstationRepository.class.getName();

    public WorkstationRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        workstationDao = databaseHelper.getWorkstationDao();
    }

    public Workstation getWorkstationById(String id) {
        try {
            return workstationDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public void clearAllWorkstations() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Workstation.class);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public int create(Workstation workstation) {
        try {
            return workstationDao.create(workstation);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public int create(List<Workstation> workstation) {
        try {
           return workstationDao.create(workstation);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    //TODO implement
    public Workstation getWorkstationForAudit(String auditId) {
        return null;
    }

    public List<Workstation> getAllWorkstations() {
        try {
            return workstationDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

}
