package lt.adient.mobility.eLPA.database.model;


import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class ModuleData {

    @DatabaseField(id = true)
    @SerializedName("IDModule")
    private String moduleId = "";
    @DatabaseField
    @SerializedName("ModuleID")
    private String moduleName = "";
    @DatabaseField
    @SerializedName("ModuleDesc")
    private String moduleDescription = "";
    @DatabaseField
    @SerializedName("IDLge")
    private Long lgeId = -1L;
    @DatabaseField
    @SerializedName("WebServiceLink")
    private String webServiceLink = "";
    @DatabaseField
    @SerializedName("DashboardLink")
    private String dashboardLink = "";
    @DatabaseField
    @SerializedName("DocumentationLink")
    private String documentationLink = "";

    public ModuleData() {
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public Long getLgeId() {
        return lgeId;
    }

    public void setLgeId(Long lgeId) {
        this.lgeId = lgeId;
    }

    public String getWebServiceLink() {
        return webServiceLink;
    }

    public void setWebServiceLink(String webServiceLink) {
        this.webServiceLink = webServiceLink;
    }

    public String getDashboardLink() {
        return dashboardLink;
    }

    public void setDashboardLink(String dashboardLink) {
        this.dashboardLink = dashboardLink;
    }

    public String getDocumentationLink() {
        return documentationLink;
    }

    public void setDocumentationLink(String documentationLink) {
        this.documentationLink = documentationLink;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleData that = (ModuleData) o;

        if (moduleId != null ? !moduleId.equals(that.moduleId) : that.moduleId != null) return false;
        if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null) return false;
        if (moduleDescription != null ? !moduleDescription.equals(that.moduleDescription) : that.moduleDescription != null) return false;
        if (lgeId != null ? !lgeId.equals(that.lgeId) : that.lgeId != null) return false;
        if (webServiceLink != null ? !webServiceLink.equals(that.webServiceLink) : that.webServiceLink != null) return false;
        if (dashboardLink != null ? !dashboardLink.equals(that.dashboardLink) : that.dashboardLink != null) return false;
        return documentationLink != null ? documentationLink.equals(that.documentationLink) : that.documentationLink == null;

    }

    @Override
    public int hashCode() {
        int result = moduleId != null ? moduleId.hashCode() : 0;
        result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
        result = 31 * result + (moduleDescription != null ? moduleDescription.hashCode() : 0);
        result = 31 * result + (lgeId != null ? lgeId.hashCode() : 0);
        result = 31 * result + (webServiceLink != null ? webServiceLink.hashCode() : 0);
        result = 31 * result + (dashboardLink != null ? dashboardLink.hashCode() : 0);
        result = 31 * result + (documentationLink != null ? documentationLink.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return moduleName;
    }
}
