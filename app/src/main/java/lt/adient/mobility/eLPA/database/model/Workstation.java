package lt.adient.mobility.eLPA.database.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Workstation {

    public static final String MACHINE_ID = "machineId";

    @DatabaseField
    @SerializedName("IDDoc")
    private String docId;

    @DatabaseField(id = true)
    @SerializedName("IDMachine")
    private String machineId;

    @DatabaseField
    @SerializedName("MachineDesc")
    private String machineDesc;


    @DatabaseField
    @SerializedName("MachineID")
    private String machineName;

    public Workstation() {

    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineDesc() {
        return machineDesc;
    }

    public void setMachineDesc(String machineDesc) {
        this.machineDesc = machineDesc;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    @Override
    public String toString() {
        return machineName;
    }
}
