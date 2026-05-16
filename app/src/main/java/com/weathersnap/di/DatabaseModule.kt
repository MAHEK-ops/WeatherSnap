package com.weathersnap.di

import android.content.Context
import androidx.room.Room
import com.weathersnap.data.local.AppDatabase
import com.weathersnap.data.local.dao.DraftReportDao
import com.weathersnap.data.local.dao.ReportDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weathersnap.db"
        ).build()

    @Provides
    fun provideReportDao(db: AppDatabase): ReportDao = db.reportDao()

    @Provides
    fun provideDraftReportDao(db: AppDatabase): DraftReportDao = db.draftReportDao()
}