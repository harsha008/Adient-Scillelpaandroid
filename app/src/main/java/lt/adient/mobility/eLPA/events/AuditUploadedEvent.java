package lt.adient.mobility.eLPA.events;

import lt.adient.mobility.eLPA.database.model.Audit;

/**
 * Created by Simonas on 2017-02-21.
 */

public class AuditUploadedEvent {

    private final Audit audit;

    public AuditUploadedEvent(Audit audit) {
        this.audit = audit;
    }

    public Audit getAudit() {
        return audit;
    }
}
