package com.weathersnap.data.repository

import com.weathersnap.data.local.entity.DraftReportEntity
import com.weathersnap.domain.model.WeatherReport
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getAllReports(): Flow<List<WeatherReport>>
    suspend fun saveReport(report: WeatherReport)
    suspend fun saveDraft(draft: DraftReportEntity)
    suspend fun getDraft(): DraftReportEntity?
    suspend fun clearDraft()
}