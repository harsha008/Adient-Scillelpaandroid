package lt.adient.mobility.eLPA.ws.RetrofitModel;

import java.util.List;

import lt.adient.mobility.eLPA.database.model.ChapterData;
import lt.adient.mobility.eLPA.database.model.User;
import lt.adient.mobility.eLPA.database.model.Workstation;

/**
 * Created by Haroldas on 10/15/2016.
 */

public class AuditResponse extends Response<List<AuditResponse.AuditResponseItem>> {

    public class AuditResponseItem {

        List<ChapterData> AuditQuestions;
        lt.adient.mobility.eLPA.database.model.AuditStatus AuditStatus;
        String IDLPAAudit;
        int Layer;
        Workstation Machine;
        String Planned;
        String Started;
        User StartedBy;
        User User;

        public AuditResponseItem() {
        }

        public List<ChapterData> getAuditQuestions() {
            return AuditQuestions;
        }

        public void setAuditQuestions(List<ChapterData> auditQuestions) {
            AuditQuestions = auditQuestions;
        }

        public   lt.adient.mobility.eLPA.database.model.AuditStatus getAuditStatus() {
            return AuditStatus;
        }

        public void setAuditStatus(  lt.adient.mobility.eLPA.database.model.AuditStatus auditStatus) {
            AuditStatus = auditStatus;
        }

        public String getIDLPAAudit() {
            return IDLPAAudit;
        }

        public void setIDLPAAudit(String IDLPAAudit) {
            this.IDLPAAudit = IDLPAAudit;
        }

        public int getLayer() {
            return Layer;
        }

        public void setLayer(int layer) {
            Layer = layer;
        }

        public Workstation getMachine() {
            return Machine;
        }

        public void setMachine(Workstation machine) {
            Machine = machine;
        }

        public String getPlanned() {
            return Planned;
        }

        public void setPlanned(String planned) {
            Planned = planned;
        }

        public String getStarted() {
            return Started;
        }

        public void setStarted(String started) {
            Started = started;
        }

        public lt.adient.mobility.eLPA.database.model.User getStartedBy() {
            return StartedBy;
        }

        public void setStartedBy(lt.adient.mobility.eLPA.database.model.User startedBy) {
            StartedBy = startedBy;
        }

        public lt.adient.mobility.eLPA.database.model.User getUser() {
            return User;
        }

        public void setUser(lt.adient.mobility.eLPA.database.model.User user) {
            User = user;
        }
    }

}
