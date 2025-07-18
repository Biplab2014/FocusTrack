package com.app.focustrack.data.dao

import androidx.room.*
import com.app.focustrack.data.entity.ScreenUnlock
import kotlinx.coroutines.flow.Flow

@Dao
interface ScreenUnlockDao {
    
    @Query("SELECT * FROM screen_unlocks WHERE date = :date ORDER BY timestamp DESC")
    fun getUnlocksByDate(date: String): Flow<List<ScreenUnlock>>
    
    @Query("SELECT COUNT(*) FROM screen_unlocks WHERE date = :date")
    fun getUnlockCountByDate(date: String): Flow<Int>
    
    @Query("SELECT * FROM screen_unlocks WHERE date BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getUnlocksBetweenDates(startDate: String, endDate: String): Flow<List<ScreenUnlock>>
    
    @Insert
    suspend fun insertUnlock(unlock: ScreenUnlock)
    
    @Delete
    suspend fun deleteUnlock(unlock: ScreenUnlock)
    
    @Query("DELETE FROM screen_unlocks WHERE date < :date")
    suspend fun deleteOldUnlocks(date: String)
}
