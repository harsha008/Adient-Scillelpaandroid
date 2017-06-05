package lt.adient.mobility.eLPA.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Calendar;

import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.AuditStatus;

public class Utility {

    public static AuditStatus getAuditStatus(Audit audit, boolean isOffline) {
        Long plannedTime = Long.parseLong(audit.getPlannedDate());
        Long starterTime = JsonHelper.getDateFromPYPFormat(audit.getStartedDate());
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        long nowDate = now.getTimeInMillis();

        if (audit.isNeeedSync() && isOffline) {
            return AuditStatus.NotSynced;
        }

        //Started
        if (starterTime != 0L) {
            if (plannedTime >= nowDate) {
                return AuditStatus.Started;
            } else {
                return AuditStatus.NotFinished;
            }
        }
        //Not started
        else {
            if (plannedTime >= nowDate) {
                return AuditStatus.Planned;
            } else {
                return AuditStatus.Overdue;
            }
        }
    }


    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnected());
        } catch (Exception ex) {
            Log.e("Utility", "Failed to check for connection");
            return false;
        }
    }

    public static void showTranslatedToast(Context context, @StringRes int resId, int duration, Translator translator) {
        Toast.makeText(context, translator.getTranslation(context, resId), duration).show();
    }

    public static String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) App.getApplicationComponent().applicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
