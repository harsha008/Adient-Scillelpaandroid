package lt.sciil.eLPA.database.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Simonas on 2016-11-28.
 */

public class ChapterData {

    @SerializedName("Chapter")
    private Chapter chapter;
    @SerializedName("QuestionData")
    private List<QuestionData> questionData;

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<QuestionData> getQuestionData() {
        return questionData;
    }

    public void setQuestionData(List<QuestionData> questionData) {
        this.questionData = questionData;
    }
}
