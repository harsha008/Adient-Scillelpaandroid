package lt.sciil.eLPA.database.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Simonas on 2016-11-28.
 */

public class QuestionData {

    @SerializedName("Question")
    private Question question;
    @SerializedName("Answer")
    private Answer answer;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
