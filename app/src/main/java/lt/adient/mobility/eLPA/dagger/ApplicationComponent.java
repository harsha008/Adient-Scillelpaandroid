package lt.adient.mobility.eLPA.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import lt.adient.mobility.eLPA.adapter.AuditListAdapter;
import lt.adient.mobility.eLPA.fragment.FilterDialog;
import lt.adient.mobility.eLPA.fragment.FullScreenDialog;
import lt.adient.mobility.eLPA.service.SaveAuditService;
import lt.adient.mobility.eLPA.utils.PrefManager;

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
