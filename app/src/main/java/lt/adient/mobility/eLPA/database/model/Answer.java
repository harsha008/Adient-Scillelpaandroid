package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;


public class Answer {

    public static final String PHOTO_SENT = "photoSent";
    public static final String NEED_SYNC = "needSync";
    public static final String OK = "ok";
    public static final String NOK = "notOk";

    @DatabaseField(id = true, useGetSet = true)
    private String id;
    @DatabaseField
    @SerializedName("Answered")
    private String answeredDate;
    @DatabaseField
    @SerializedName("Closed")
    private int closed;
    @DatabaseField
    @SerializedName("IDAnsweredBy")
    private String answeredByUserId;
    @DatabaseField
    @SerializedName("IDDoc")
    private String docId;
    @DatabaseField
    @SerializedName("IDLPAAudit")
    private String auditId;
    @DatabaseField
    @SerializedName("ImmediatelyCorrected")
    private int immediatelyCorrected;
    @DatabaseField
    @SerializedName("Info1")
    private String info;
    @DatabaseField
    @SerializedName("NotOk")
    private int notOk;
    @DatabaseField
    @SerializedName("Ok")
    private int ok;
    @DatabaseField
    @SerializedName("IDQuestion")
    private String questionId;

    @DatabaseField
    @Expose(serialize = false, deserialize = false)
    private boolean photoSent = true;

    @DatabaseField
    @Expose(serialize = false, deserialize = false)
    private boolean needSync = false;

    @DatabaseField
    @Expose(deserialize = false, serialize = false)
    private String chapterId;

    @Expose(deserialize = false, serialize = false)
    private boolean deletePhotoOnSave;

    public Answer() {
    }

    public String getId() {
        return questionId + auditId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnsweredDate() {
        return answeredDate;
    }

    public void setAnsweredDate(String answeredDate) {
        this.answeredDate = answeredDate;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public String getAnsweredByUserId() {
        return answeredByUserId;
    }

    public void setAnsweredByUserId(String answeredByUserId) {
        this.answeredByUserId = answeredByUserId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public int getImmediatelyCorrected() {
        return immediatelyCorrected;
    }

    public void setImmediatelyCorrected(int immediatelyCorrected) {
        this.immediatelyCorrected = immediatelyCorrected;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getNotOk() {
        return notOk;
    }

    public void setNotOk(int notOk) {
        this.notOk = notOk;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public boolean isPhotoSent() {
        return photoSent;
    }

    public void setPhotoSent(boolean photoSent) {
        this.photoSent = photoSent;
    }

    public boolean isNeedSync() {
        return needSync;
    }

    public void setNeedSync(boolean neeedSync) {
        this.needSync = neeedSync;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return getId().equals(answer.getId());

    }

    @Override
    public int hashCode() {
        return questionId.hashCode();
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public boolean isDeletePhotoOnSave() {
        return deletePhotoOnSave;
    }

    public void setDeletePhotoOnSave(boolean deletePhotoOnSave) {
        this.deletePhotoOnSave = deletePhotoOnSave;
    }
}
