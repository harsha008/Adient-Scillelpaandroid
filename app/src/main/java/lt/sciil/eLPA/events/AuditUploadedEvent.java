package lt.sciil.eLPA.events;

import lt.sciil.eLPA.database.model.Audit;

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
