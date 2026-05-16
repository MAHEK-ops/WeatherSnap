package com.weathersnap.util

import android.content.Context
import java.io.File
import java.util.UUID

object FileUtils {

    /**
     * Creates a temp file in cacheDir for CameraX raw capture output.
     * This file is deleted immediately after compression.
     */
    fun createTempCaptureFile(context: Context): File {
        val dir = context.cacheDir
        return File(dir, "temp_capture_${UUID.randomUUID()}.jpg")
    }

    /**
     * Creates the final compressed report image file in filesDir.
     * This file persists as long as the report exists.
     */
    fun createReportImageFile(context: Context): File {
        val dir = File(context.filesDir, "reports").apply { mkdirs() }
        return File(dir, "report_${UUID.randomUUID()}.jpg")
    }

    /**
     * Safely deletes a file if it exists.
     */
    fun deleteIfExists(path: String?) {
        if (path.isNullOrBlank()) return
        val file = File(path)
        if (file.exists()) file.delete()
    }
}