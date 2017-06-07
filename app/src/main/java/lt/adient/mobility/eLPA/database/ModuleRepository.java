package lt.adient.mobility.eLPA.database;


import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.ModuleData;

public class ModuleRepository {

    private final DatabaseHelper databaseHelper;
    private final Dao<ModuleData, String> moduleDao;
    private static final String TAG = ModuleRepository.class.getName();

    public ModuleRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        moduleDao = databaseHelper.getModuleDao();
    }

    public int create(List<ModuleData> moduleDatas) {
        try {
            return moduleDao.create(moduleDatas);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public int create(ModuleData moduleData) {
        try {
            return moduleDao.create(moduleData);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public ModuleData get(String id) {
        try {
            return moduleDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public boolean isEmpty() {
        try {
            PreparedQuery<ModuleData> query = moduleDao
                    .queryBuilder()
                    .setCountOf(true)
                    .prepare();
            long count = moduleDao.countOf(query);
            return count == 0;
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return true;
    }

    public List<ModuleData> getAll() {
        try {
            return moduleDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void deleteTable() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), ModuleData.class);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }
}
