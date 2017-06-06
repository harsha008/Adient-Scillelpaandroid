package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import lt.adient.mobility.eLPA.ws.RetrofitModel.AuditResponse;


public class Audit {

    public static final String AUDIT_ID = "auditId";
    public static final String AUDIT_NEED_SYNC = "neeedSync";


    @DatabaseField
    @SerializedName("Planned")
    private String plannedDate;
    @DatabaseField
    @SerializedName("User")
    private String userId;
    @DatabaseField
    @SerializedName("Machine")
    private String machineId;
    @DatabaseField
    @SerializedName("AuditStatus")
    private AuditStatus auditStatus;
    @DatabaseField(id = true)
    @SerializedName("IDLPAAudit")
    private String auditId;
    @DatabaseField
    @SerializedName("Started")
    private String startedDate;
    @DatabaseField
    @SerializedName("StartedBy")
    private String startedByUserId;
    @DatabaseField
    @SerializedName("Layer")
    private int layer;
    @DatabaseField
    private boolean neeedSync = false;


    public Audit() {
    }

    public Audit(AuditResponse.AuditResponseItem responseItem) {
        userId = responseItem.getUser().getUserId();
        machineId = responseItem.getMachine().getMachineId();
        auditStatus = responseItem.getAuditStatus();
        auditId = responseItem.getIDLPAAudit();
        startedDate = responseItem.getStarted();
        startedByUserId = responseItem.getStartedBy().getUserId();
        layer = responseItem.getLayer();
        String planned = responseItem.getPlanned();
        plannedDate = planned.substring(6, planned.length() - 2);
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public AuditStatus getAuditStatus() {
        if (auditStatus == null)
            return AuditStatus.Unknown;
        return auditStatus;
    }

    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getStartedByUserId() {
        return startedByUserId;
    }

    public void setStartedByUserId(String startedByUserId) {
        this.startedByUserId = startedByUserId;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public boolean isNeeedSync() {
        return neeedSync;
    }

    public void setNeeedSync(boolean neeedSync) {
        this.neeedSync = neeedSync;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audit audit = (Audit) o;

        return auditId.equals(audit.auditId);

    }

    @Override
    public int hashCode() {
        return auditId.hashCode();
    }
}
