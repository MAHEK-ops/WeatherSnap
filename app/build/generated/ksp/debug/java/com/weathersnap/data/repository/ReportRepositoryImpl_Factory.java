package com.weathersnap.data.repository;

import com.weathersnap.data.local.dao.DraftReportDao;
import com.weathersnap.data.local.dao.ReportDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ReportRepositoryImpl_Factory implements Factory<ReportRepositoryImpl> {
  private final Provider<ReportDao> reportDaoProvider;

  private final Provider<DraftReportDao> draftReportDaoProvider;

  public ReportRepositoryImpl_Factory(Provider<ReportDao> reportDaoProvider,
      Provider<DraftReportDao> draftReportDaoProvider) {
    this.reportDaoProvider = reportDaoProvider;
    this.draftReportDaoProvider = draftReportDaoProvider;
  }

  @Override
  public ReportRepositoryImpl get() {
    return newInstance(reportDaoProvider.get(), draftReportDaoProvider.get());
  }

  public static ReportRepositoryImpl_Factory create(Provider<ReportDao> reportDaoProvider,
      Provider<DraftReportDao> draftReportDaoProvider) {
    return new ReportRepositoryImpl_Factory(reportDaoProvider, draftReportDaoProvider);
  }

  public static ReportRepositoryImpl newInstance(ReportDao reportDao,
      DraftReportDao draftReportDao) {
    return new ReportRepositoryImpl(reportDao, draftReportDao);
  }
}
