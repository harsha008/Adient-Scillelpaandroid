package lt.adient.mobility.eLPA.database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.Question;

public class QuestionRepository {


    private final DatabaseHelper databaseHelper;
    private final Dao<Question, String> questionDao;
    private static final String TAG = QuestionRepository.class.getName();

    public QuestionRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        questionDao = databaseHelper.getQuestionDao();
    }

    public Question getQuestionById(String id) {
        try {
            return questionDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public Question getQuestionForAudit(String questionId, String auditId) {
        try {
            PreparedQuery<Question> preparedQuery = questionDao.queryBuilder()
                    .where()
                    .eq(Question.QUESTION_ID, questionId)
                    .and()
                    .eq(Audit.AUDIT_ID, auditId)
                    .prepare();
            return questionDao.queryForFirst(preparedQuery);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public List<Question> getAllQuestionsForAudit(String auditId) {
        try {
            return questionDao.queryForEq(Audit.AUDIT_ID, auditId);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public List<Question> getAll() {
        try {
            return questionDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public long getDownloadedQuestionCount(String auditId) {
        try {
            PreparedQuery<Question> query = questionDao
                    .queryBuilder()
                    .setCountOf(true)
                    .where()
                    .eq(Audit.AUDIT_ID, auditId)
                    .prepare();
            return questionDao.countOf(query);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public void clearAllQuestions() {
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), Question.class);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Question question) {
        try {
            return questionDao.createOrUpdate(question);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }
}
