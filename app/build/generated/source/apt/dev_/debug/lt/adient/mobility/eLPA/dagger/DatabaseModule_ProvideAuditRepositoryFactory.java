// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package lt.adient.mobility.eLPA.dagger;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import lt.adient.mobility.eLPA.database.AuditRepository;
import lt.adient.mobility.eLPA.database.DatabaseHelper;

public final class DatabaseModule_ProvideAuditRepositoryFactory
    implements Factory<AuditRepository> {
  private final DatabaseModule module;

  private final Provider<DatabaseHelper> databaseHelperProvider;

  public DatabaseModule_ProvideAuditRepositoryFactory(
      DatabaseModule module, Provider<DatabaseHelper> databaseHelperProvider) {
    assert module != null;
    this.module = module;
    assert databaseHelperProvider != null;
    this.databaseHelperProvider = databaseHelperProvider;
  }

  @Override
  public AuditRepository get() {
    return Preconditions.checkNotNull(
        module.provideAuditRepository(databaseHelperProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AuditRepository> create(
      DatabaseModule module, Provider<DatabaseHelper> databaseHelperProvider) {
    return new DatabaseModule_ProvideAuditRepositoryFactory(module, databaseHelperProvider);
  }
}