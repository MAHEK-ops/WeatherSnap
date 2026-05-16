package com.weathersnap.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

data class CompressionResult(
    val compressedFile: File,
    val originalSizeKb: Long,
    val compressedSizeKb: Long
)

class ImageCompressor @Inject constructor() {

    /**
     * Compresses a captured image file.
     * - Reads original bitmap
     * - Scales it down to max 1024px width
     * - Saves as JPEG at 60% quality
     * - Returns both original and compressed sizes in KB
     *
     * Always runs on IO dispatcher — never called from main thread.
     */
    suspend fun compress(
        context: Context,
        sourceFile: File,
        targetFile: File,
        quality: Int = 60,
        maxWidth: Int = 1024
    ): CompressionResult = withContext(Dispatchers.IO) {

        val originalSizeKb = sourceFile.length() / 1024

        // Decode original bitmap
        val original = BitmapFactory.decodeFile(sourceFile.absolutePath)

        // Scale down if wider than maxWidth
        val scaled = if (original.width > maxWidth) {
            val ratio = maxWidth.toFloat() / original.width
            val newHeight = (original.height * ratio).toInt()
            Bitmap.createScaledBitmap(original, maxWidth, newHeight, true)
        } else {
            original
        }

        // Write compressed file
        FileOutputStream(targetFile).use { out ->
            scaled.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }

        // Recycle bitmaps
        if (scaled != original) scaled.recycle()
        original.recycle()

        val compressedSizeKb = targetFile.length() / 1024

        CompressionResult(
            compressedFile = targetFile,
            originalSizeKb = originalSizeKb,
            compressedSizeKb = compressedSizeKb
        )
    }
}