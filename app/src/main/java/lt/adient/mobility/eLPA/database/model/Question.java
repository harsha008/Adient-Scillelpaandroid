package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Question {
    public static final String QUESTION_ID = "questionId";

    @SerializedName("IDQuestion")
    @DatabaseField(id = true)
    private String questionId;

    @DatabaseField
    private int questionItemId;
    @DatabaseField
    @SerializedName("IDDoc")
    private String docId;
    @DatabaseField
    @SerializedName("IDParentQuestion")
    private String parentQuestionId;
    @DatabaseField
    @SerializedName("IDLPAAudit")
    private String auditId;

    @DatabaseField
    @SerializedName("Info1")
    private String info;
    @DatabaseField
    @SerializedName("QuestionDesc")
    private String questionDesc;
    @DatabaseField
    @SerializedName("QuestionID")
    private String questionName;

    @DatabaseField
    @Expose(deserialize = false, serialize = false)
    private String chapterId;


    public Question() {
    }

    public int getQuestionItemId() {
        return questionItemId;
    }

    public void setQuestionItemId(int questionItemId) {
        this.questionItemId = questionItemId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(String parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
