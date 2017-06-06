package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.SerializedName;

public class FilterParameters {

    @SerializedName("IDMachine")
    private String machineId;
    @SerializedName("IDUser")
    private String userId;
    @SerializedName("Planned")
    private Planned planned;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Planned getPlanned() {
        return planned;
    }

    public void setPlanned(Planned planned) {
        this.planned = planned;
    }

    public void setNullDate() {
        Planned planned = new Planned();
        setPlanned(planned);
    }
}
