package lt.adient.mobility.eLPA.database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.User;

/**
 * Created by Simonas on 2017-01-09.
 */

public class UserRepository {

    private final DatabaseHelper databaseHelper;
    private final Dao<User, String> userDao;
    private static final String TAG = UserRepository.class.getName();

    public UserRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        userDao = databaseHelper.getUserDao();
    }

    public User getUserById(String id) {
        try {
            return userDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public void clearAllUsers() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), User.class);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public int create(User user) {
        try {
            return userDao.create(user);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public int create(List<User> user) {
        try {
            return userDao.create(user);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public List<User> getAllUsers() {
        try {
            return userDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }
}
