package lt.sciil.eLPA.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import lt.sciil.eLPA.adapter.AuditListAdapter;
import lt.sciil.eLPA.fragment.FilterDialog;
import lt.sciil.eLPA.fragment.FullScreenDialog;
import lt.sciil.eLPA.service.SaveAuditService;
import lt.sciil.eLPA.utils.PrefManager;

@Singleton
@Component(modules = {DatabaseModule.class, AndroidModule.class, NetModule.class})
public interface ApplicationComponent {

    void inject(FullScreenDialog fragment);

    void inject(FilterDialog fragment);

    void inject(AuditListAdapter auditListAdapter);

    void inject(SaveAuditService saveAuditService);

    PrefManager getPrefManager();

    NonConfigurationComponent nonConfiguration();

    Context applicationContext();

}
