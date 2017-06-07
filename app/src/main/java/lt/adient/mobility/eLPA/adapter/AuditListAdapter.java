package lt.adient.mobility.eLPA.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import lt.adient.mobility.eLPA.App;
import lt.adient.mobility.eLPA.R;
import lt.adient.mobility.eLPA.activity.question.QuestionsActivity;
import lt.adient.mobility.eLPA.database.QuestionRepository;
import lt.adient.mobility.eLPA.database.UserRepository;
import lt.adient.mobility.eLPA.database.WorkstationRepository;
import lt.adient.mobility.eLPA.database.model.Audit;
import lt.adient.mobility.eLPA.database.model.User;
import lt.adient.mobility.eLPA.database.model.Workstation;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.utils.Translator;
import lt.adient.mobility.eLPA.utils.Utility;


public class AuditListAdapter extends RecyclerView.Adapter<AuditListAdapter.ViewHolder> {
    private static final String AUDIT_ID = "auditId";
    private final DateFormat dateFormatter;
    private final Activity activity;

    private final List<Audit> auditsList;

    @Inject
    Translator translator;
    @Inject
    UserRepository userRepository;
    @Inject
    WorkstationRepository workstationRepository;
    @Inject
    PrefManager prefManager;
    @Inject
    QuestionRepository questionRepository;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView user;
        public final TextView userLabel;
        public final TextView workstation;
        public final TextView workstationLabel;
        public final TextView date;
        public final TextView status;
        public final TextView dateLabel;
        public final ImageView circle;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            user = (TextView) view.findViewById(R.id.user_textView);
            userLabel = (TextView) view.findViewById(R.id.userLabel);
            workstation = (TextView) view.findViewById(R.id.workstation_textView);
            workstationLabel = (TextView) view.findViewById(R.id.workstationLabel);
            date = (TextView) view.findViewById(R.id.date_textView);
            dateLabel = (TextView) view.findViewById(R.id.plannedDateLabel);
            status = (TextView) view.findViewById(R.id.status_text_view);
            circle = (ImageView) view.findViewById(R.id.circle_image_view);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position >= 0) {
                Audit audit = auditsList.get(position);
                checkIfCanOpenAudit(audit);
            }
        }
    }

    public AuditListAdapter(List<Audit> audits, Activity activity) {
        this.auditsList = audits;
        this.dateFormatter = android.text.format.DateFormat.getDateFormat(activity);
        this.activity = activity;
        App.getApplicationComponent().inject(this);

    }

    @Override
    public AuditListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audit_list_item, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Audit audit = auditsList.get(position);
        Workstation workstation = workstationRepository.getWorkstationById(audit.getMachineId());
        User user = userRepository.getUserById(audit.getUserId());

        if (user != null) {
            holder.user.setText(user.getUserName());
        }

        if (workstation != null) {
            holder.workstation.setText(workstation.getMachineName());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(audit.getPlannedDate(), 10));
        holder.date.setText(dateFormatter.format(calendar.getTime()));

        GradientDrawable circle = (GradientDrawable) holder.circle.getBackground();
        boolean isOffline = prefManager.isOffline();

        switch (Utility.getAuditStatus(audit, isOffline)) {
            case Planned:
                holder.date.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorAccent));
                holder.dateLabel.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorAccent));
                holder.status.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorAccent));
                holder.status.setTag("status_planned");
                holder.status.setText(R.string.status_planned);
                circle.setColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorAccent));
                break;
            case Started:
                holder.date.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                holder.dateLabel.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                holder.status.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                holder.status.setTag("status_started");
                holder.status.setText(activity.getString(R.string.status_started));
                circle.setColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                break;
            case NotFinished:
                holder.date.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                holder.dateLabel.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                holder.status.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                holder.status.setTag("status_not_finished");
                holder.status.setText(R.string.status_not_finished);
                circle.setColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotFinished));
                break;
            case Overdue:
                holder.date.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                holder.dateLabel.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                holder.status.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                holder.status.setTag("status_overdue");
                holder.status.setText(R.string.status_overdue);
                circle.setColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                break;
            case NotSynced:
                holder.date.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                holder.dateLabel.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                holder.status.setTextColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                holder.status.setTag("status_not_synced");
                holder.status.setText(R.string.status_not_synced);
                circle.setColor(ContextCompat.getColor(holder.date.getContext(), R.color.colorNotConnected));
                break;
        }
        translate(holder);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return auditsList.size();
    }

    private void translate(ViewHolder holder) {
//        translator.translate(holder.userLabel,
//                holder.workstationLabel,
//                holder.dateLabel,
//                holder.status);
        holder.userLabel.setText(translator.getTranslation((String) holder.userLabel.getTag()));
        holder.workstationLabel.setText(translator.getTranslation((String) holder.workstationLabel.getTag()));
        holder.dateLabel.setText(translator.getTranslation((String) holder.dateLabel.getTag()));
        holder.status.setText(translator.getTranslation((String) holder.status.getTag()));
    }

    private void checkIfCanOpenAudit(Audit audit) {
        String userId = prefManager.getUserId();
        String startedByUser = audit.getStartedByUserId();
        if (startedByUser != null && !startedByUser.isEmpty() && !userId.equals(startedByUser)) {
            User user = userRepository.getUserById(startedByUser);
            String dialogMessage = translator.getTranslation(activity, R.string.audit_started_by);
            if (user != null) {
                dialogMessage += " " + user.getUserName();
            } else {
                dialogMessage += " " + translator.getTranslation(activity, R.string.can_not_find_user) + startedByUser;
                Crashlytics.log("Can not find user with id:" + startedByUser);
            }
            final AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.CustomDialog)
                    .setTitle(translator.getTranslation(activity, R.string.audit_wrong_user_alert_title))
                    .setMessage(dialogMessage)
                    .setCancelable(false)
                    .setPositiveButton(translator.getTranslation(activity, R.string.ok), (dialog, which) -> dialog.dismiss())
                    .create();
            alertDialog.show();

        } else if (questionRepository.getDownloadedQuestionCount(audit.getAuditId()) < 1 && prefManager.isOffline()) {
            final AlertDialog alertDialog = new AlertDialog.Builder(activity, R.style.CustomDialog)
                    .setTitle(translator.getTranslation(activity, R.string.offline_mode_on))
                    .setMessage(translator.getTranslation(activity, R.string.can_not_open_audit_offline))
                    .setCancelable(false)
                    .setPositiveButton(translator.getTranslation(activity, R.string.ok), (dialog, which) -> dialog.dismiss())
                    .create();
            alertDialog.show();
        } else {
            Intent intent = new Intent(activity, QuestionsActivity.class);
            Bundle args = new Bundle();
            args.putString(AUDIT_ID, audit.getAuditId());
            intent.putExtras(args);
            activity.startActivity(intent);
        }
    }
}
