// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package lt.adient.mobility.eLPA.activity.login;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;
import lt.adient.mobility.eLPA.database.ModuleRepository;
import lt.adient.mobility.eLPA.database.UserRepository;
import lt.adient.mobility.eLPA.database.WorkstationRepository;
import lt.adient.mobility.eLPA.utils.PrefManager;
import lt.adient.mobility.eLPA.ws.SciilAPI;

public final class LoginPresenter_Factory implements Factory<LoginPresenter> {
  private final MembersInjector<LoginPresenter> loginPresenterMembersInjector;

  private final Provider<PrefManager> prefManagerProvider;

  private final Provider<SciilAPI> sciilAPIProvider;

  private final Provider<ModuleRepository> moduleRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<WorkstationRepository> workstationRepositoryProvider;

  public LoginPresenter_Factory(
      MembersInjector<LoginPresenter> loginPresenterMembersInjector,
      Provider<PrefManager> prefManagerProvider,
      Provider<SciilAPI> sciilAPIProvider,
      Provider<ModuleRepository> moduleRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<WorkstationRepository> workstationRepositoryProvider) {
    assert loginPresenterMembersInjector != null;
    this.loginPresenterMembersInjector = loginPresenterMembersInjector;
    assert prefManagerProvider != null;
    this.prefManagerProvider = prefManagerProvider;
    assert sciilAPIProvider != null;
    this.sciilAPIProvider = sciilAPIProvider;
    assert moduleRepositoryProvider != null;
    this.moduleRepositoryProvider = moduleRepositoryProvider;
    assert userRepositoryProvider != null;
    this.userRepositoryProvider = userRepositoryProvider;
    assert workstationRepositoryProvider != null;
    this.workstationRepositoryProvider = workstationRepositoryProvider;
  }

  @Override
  public LoginPresenter get() {
    return MembersInjectors.injectMembers(
        loginPresenterMembersInjector,
        new LoginPresenter(
            prefManagerProvider.get(),
            sciilAPIProvider.get(),
            moduleRepositoryProvider.get(),
            userRepositoryProvider.get(),
            workstationRepositoryProvider.get()));
  }

  public static Factory<LoginPresenter> create(
      MembersInjector<LoginPresenter> loginPresenterMembersInjector,
      Provider<PrefManager> prefManagerProvider,
      Provider<SciilAPI> sciilAPIProvider,
      Provider<ModuleRepository> moduleRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<WorkstationRepository> workstationRepositoryProvider) {
    return new LoginPresenter_Factory(
        loginPresenterMembersInjector,
        prefManagerProvider,
        sciilAPIProvider,
        moduleRepositoryProvider,
        userRepositoryProvider,
        workstationRepositoryProvider);
  }
}
