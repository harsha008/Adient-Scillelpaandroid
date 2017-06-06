package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Chapter {
    public static String CHAPTER_ID = "chapterId";
    @DatabaseField
    @SerializedName("ChapterDesc")
    private String chapterDesc;
    @DatabaseField
    @SerializedName("ChapterID")
    private String chapterName;
    @DatabaseField(id = true)
    @SerializedName("IDChapter")
    private String chapterId;
    @DatabaseField
    @SerializedName("Info1")
    private String info;
    @DatabaseField
    @Expose(deserialize = false, serialize = false)
    private String auditId;

    public Chapter() {
    }


    public String getChapterDesc() {
        return chapterDesc;
    }

    public void setChapterDesc(String chapterDesc) {
        this.chapterDesc = chapterDesc;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }
}
