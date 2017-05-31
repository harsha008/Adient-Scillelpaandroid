package lt.sciil.eLPA.database.model;


import com.google.gson.annotations.SerializedName;

public class Planned {

    @SerializedName("DateFrom")
    private String dateFrom;
    @SerializedName("DateTo")
    private String dateTo;

    public Planned() {
    }

    public Planned(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }
}
