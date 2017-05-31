package lt.sciil.eLPA.utils;

import java.util.ArrayList;
import java.util.List;

import lt.sciil.eLPA.database.model.Audit;
import lt.sciil.eLPA.ws.RetrofitModel.AuditResponse;

public class JsonHelper {

    public static List<Audit> getAuditsListFromRetrofit(List<AuditResponse.AuditResponseItem> result1) {
        List<Audit> audits = new ArrayList<>();
        for (AuditResponse.AuditResponseItem responseItem : result1) {
            audits.add(new Audit(responseItem));
        }
        return audits;
    }

    public static String formatPYPFormat(Long timeInMillis) {
        if (timeInMillis == null || timeInMillis == 0) {
            return null;
        }
        return "/Date(" + timeInMillis + ")/";
    }

    public static Long getDateFromPYPFormat(String date) {
        if (date == null)
            return 0L;
        return Long.parseLong(date.substring(6, date.length() - 2));
    }

}
