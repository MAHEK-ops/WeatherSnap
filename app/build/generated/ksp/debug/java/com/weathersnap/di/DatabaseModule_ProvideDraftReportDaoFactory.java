package com.weathersnap.di;

import com.weathersnap.data.local.AppDatabase;
import com.weathersnap.data.local.dao.DraftReportDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DatabaseModule_ProvideDraftReportDaoFactory implements Factory<DraftReportDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideDraftReportDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DraftReportDao get() {
    return provideDraftReportDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideDraftReportDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideDraftReportDaoFactory(dbProvider);
  }

  public static DraftReportDao provideDraftReportDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDraftReportDao(db));
  }
}
