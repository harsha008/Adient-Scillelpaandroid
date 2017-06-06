package lt.adient.mobility.eLPA.database.model;


import com.google.gson.annotations.SerializedName;

public enum AuditStatus {
    @SerializedName("0")
    Planned(0),
    @SerializedName("1")
    Confirmed(1),
    @SerializedName("2")
    Started(2),
    @SerializedName("3")
    Audited(3),
    @SerializedName("4")
    Overdue(4),
    @SerializedName("5")
    NotFinished(5),
    @SerializedName("6")
    NotSynced(6),
    Unknown(-1);

    private final int status;


    AuditStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
