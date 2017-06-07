package lt.adient.mobility.eLPA.database;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.Question;

public class AnswerRepository {
    private final DatabaseHelper databaseHelper;
    private final Dao<Answer, String> answerDao;
    private static final String TAG = AnswerRepository.class.getName();

    public AnswerRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        answerDao = databaseHelper.getAnswerDao();
    }

    public Answer getAnswerById(String id) {
        try {
            return answerDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public Answer getAnswerForQuestion(String questionId, String auditId) {
        try {
            QueryBuilder<Answer, String> answerQueryBuilder = answerDao.queryBuilder();
            answerQueryBuilder.where().eq(Question.QUESTION_ID, questionId).and().eq(Audit.AUDIT_ID, auditId);
            PreparedQuery<Answer> answerPreparedQuery = answerQueryBuilder.prepare();
            return answerDao.queryForFirst(answerPreparedQuery);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return null;
    }

    public List<Answer> getAnswersForAudit(String auditId) {
        try {
            return answerDao.queryForEq(Audit.AUDIT_ID, auditId);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public int update(Answer answer) {
        try {
            return answerDao.update(answer);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public List<Answer> getNotSyncedAnswers(String auditId) {
        try {
            PreparedQuery<Answer> query = answerDao
                    .queryBuilder()
                    .where()
                    .eq(Answer.NEED_SYNC, true)
                    .and()
                    .eq(Audit.AUDIT_ID, auditId)
                    .prepare();
            return answerDao.query(query);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public long getNotAnsweredCount(String auditId) {
        try {
            PreparedQuery<Answer> query = answerDao
                    .queryBuilder()
                    .setCountOf(true)
                    .where()
                    .eq(Answer.OK, 1)
                    .or()
                    .eq(Answer.NOK, 1)
                    .and()
                    .eq(Audit.AUDIT_ID, auditId)
                    .prepare();
            long count = answerDao.countOf(query);
            return count;
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public List<Answer> getNotAnswered(String auditId) {
        try {
            PreparedQuery<Answer> query = answerDao
                    .queryBuilder()
                    .where()
                    .eq(Answer.OK, 1)
                    .or()
                    .eq(Answer.NOK, 1)
                    .and()
                    .eq(Audit.AUDIT_ID, auditId)
                    .prepare();
            return answerDao.query(query);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public int deleteSyncedAnswers() {
        try {
            DeleteBuilder<Answer, String> deleteBuilder = answerDao.deleteBuilder();
            deleteBuilder.where().eq(Answer.NEED_SYNC, false);
            return answerDao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Answer> getSyncedAsnwers() {
        try {
            return answerDao.queryForEq(Answer.NEED_SYNC, false);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return Collections.emptyList();
    }

    public int delete(Answer answer) {
        try {
            return answerDao.delete(answer);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return -1;
    }

    public int delete(List<Answer> answers) {
        try {
            return answerDao.delete(answers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Answer answer) {
        try {
            return answerDao.createOrUpdate(answer);
        } catch (SQLException e) {
            Log.e(TAG, "Exception", e);
        }
        return new Dao.CreateOrUpdateStatus(false, false, -1);
    }

    public List<Answer> getNotSentPhotos() {
        try {
            return answerDao.queryForEq(Answer.PHOTO_SENT, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
