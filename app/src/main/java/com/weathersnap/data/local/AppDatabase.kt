package com.weathersnap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weathersnap.data.local.dao.DraftReportDao
import com.weathersnap.data.local.dao.ReportDao
import com.weathersnap.data.local.entity.DraftReportEntity
import com.weathersnap.data.local.entity.ReportEntity

@Database(
    entities = [ReportEntity::class, DraftReportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
    abstract fun draftReportDao(): DraftReportDao
}