package com.weathersnap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weathersnap.data.local.entity.DraftReportEntity

@Dao
interface DraftReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDraft(draft: DraftReportEntity)

    @Query("SELECT * FROM draft_report WHERE id = 1")
    suspend fun getDraft(): DraftReportEntity?

    @Query("DELETE FROM draft_report WHERE id = 1")
    suspend fun clearDraft()
}