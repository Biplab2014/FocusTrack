package com.app.focustrack.data.dao

import androidx.room.*
import com.app.focustrack.data.entity.AppUsage
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
    
    @Query("SELECT * FROM app_usage WHERE date = :date ORDER BY totalTimeInForeground DESC")
    fun getUsageByDate(date: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM app_usage WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, totalTimeInForeground DESC")
    fun getUsageBetweenDates(startDate: String, endDate: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM app_usage WHERE date = :date ORDER BY totalTimeInForeground DESC LIMIT :limit")
    fun getTopUsageByDate(date: String, limit: Int): Flow<List<AppUsage>>
    
    @Query("SELECT SUM(totalTimeInForeground) FROM app_usage WHERE date = :date")
    fun getTotalScreenTimeByDate(date: String): Flow<Long?>
    
    @Query("SELECT SUM(launchCount) FROM app_usage WHERE date = :date")
    fun getTotalLaunchCountByDate(date: String): Flow<Int?>
    
    @Query("SELECT * FROM app_usage WHERE packageName = :packageName AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getAppUsageHistory(packageName: String, startDate: String, endDate: String): Flow<List<AppUsage>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsage(usage: AppUsage)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsages(usages: List<AppUsage>)
    
    @Update
    suspend fun updateUsage(usage: AppUsage)
    
    @Delete
    suspend fun deleteUsage(usage: AppUsage)
    
    @Query("DELETE FROM app_usage WHERE date < :date")
    suspend fun deleteOldUsage(date: String)
}
