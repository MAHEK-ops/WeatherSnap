package com.weathersnap.data.repository

import com.weathersnap.data.local.dao.DraftReportDao
import com.weathersnap.data.local.dao.ReportDao
import com.weathersnap.data.local.entity.DraftReportEntity
import com.weathersnap.data.local.entity.ReportEntity
import com.weathersnap.domain.model.WeatherReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDao: ReportDao,
    private val draftReportDao: DraftReportDao
) : ReportRepository {

    override fun getAllReports(): Flow<List<WeatherReport>> =
        reportDao.getAllReports().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun saveReport(report: WeatherReport) {
        withContext(Dispatchers.IO) {
            reportDao.insertReport(report.toEntity())
        }
    }

    override suspend fun saveDraft(draft: DraftReportEntity) {
        withContext(Dispatchers.IO) {
            draftReportDao.saveDraft(draft)
        }
    }

    override suspend fun getDraft(): DraftReportEntity? =
    withContext(Dispatchers.IO) {
        draftReportDao.getDraft()
    }

    override suspend fun clearDraft() {
        withContext(Dispatchers.IO) {
            draftReportDao.clearDraft()
        }
    }

    private fun ReportEntity.toDomain() = WeatherReport(
        id = id,
        cityName = cityName,
        country = country,
        temperature = temperature,
        condition = condition,
        humidity = humidity,
        windSpeed = windSpeed,
        pressure = pressure,
        imagePath = imagePath,
        originalSizeKb = originalSizeKb,
        compressedSizeKb = compressedSizeKb,
        notes = notes,
        timestamp = timestamp
    )

    private fun WeatherReport.toEntity() = ReportEntity(
        id = id,
        cityName = cityName,
        country = country,
        temperature = temperature,
        condition = condition,
        humidity = humidity,
        windSpeed = windSpeed,
        pressure = pressure,
        imagePath = imagePath,
        originalSizeKb = originalSizeKb,
        compressedSizeKb = compressedSizeKb,
        notes = notes,
        timestamp = timestamp
    )
}