package lt.sciil.eLPA.dagger;

import dagger.Subcomponent;
import lt.sciil.eLPA.activity.auditList.AuditListActivity;
import lt.sciil.eLPA.activity.login.LoginActivity;
import lt.sciil.eLPA.activity.question.QuestionsActivity;
import lt.sciil.eLPA.activity.settings.SettingsActivity;
import lt.sciil.eLPA.fragment.QuestionFragmentMVP;

@Subcomponent
@NonConfigurationScope
public interface NonConfigurationComponent {
    void inject(LoginActivity activity);

    void inject(SettingsActivity activity);

    void inject(AuditListActivity activity);

    void inject(QuestionsActivity activity);

    void inject(QuestionFragmentMVP questionFragmentMVP);
}
