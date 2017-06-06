package lt.adient.mobility.eLPA.dagger;

import dagger.Subcomponent;
import lt.adient.mobility.eLPA.activity.auditList.AuditListActivity;
import lt.adient.mobility.eLPA.activity.login.LoginActivity;
import lt.adient.mobility.eLPA.activity.question.QuestionsActivity;
import lt.adient.mobility.eLPA.activity.settings.SettingsActivity;
import lt.adient.mobility.eLPA.fragment.QuestionFragmentMVP;

@Subcomponent
@NonConfigurationScope
public interface NonConfigurationComponent {
    void inject(LoginActivity activity);

    void inject(SettingsActivity activity);

    void inject(AuditListActivity activity);

    void inject(QuestionsActivity activity);

    void inject(QuestionFragmentMVP questionFragmentMVP);
}
