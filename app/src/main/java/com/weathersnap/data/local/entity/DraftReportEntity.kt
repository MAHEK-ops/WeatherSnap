package com.weathersnap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Single-row staging table for draft recovery.
 * Always uses id = 1 — only one draft exists at a time.
 * Deleted atomically when report is saved or discarded.
 */
@Entity(tableName = "draft_report")
data class DraftReportEntity(
    @PrimaryKey
    val id: Int = 1,
    val weatherJson: String,       // serialized WeatherData snapshot
    val imagePath: String = "",    // compressed image path
    val originalSizeKb: Long = 0,
    val compressedSizeKb: Long = 0,
    val notes: String = ""
)