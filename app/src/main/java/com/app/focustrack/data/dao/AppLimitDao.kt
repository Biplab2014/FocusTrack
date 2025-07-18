package com.app.focustrack.data.dao

import androidx.room.*
import com.app.focustrack.data.entity.AppLimit
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLimitDao {
    
    @Query("SELECT * FROM app_limits WHERE isEnabled = 1")
    fun getEnabledLimits(): Flow<List<AppLimit>>
    
    @Query("SELECT * FROM app_limits")
    fun getAllLimits(): Flow<List<AppLimit>>
    
    @Query("SELECT * FROM app_limits WHERE packageName = :packageName")
    fun getLimitByPackage(packageName: String): Flow<AppLimit?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLimit(limit: AppLimit)
    
    @Update
    suspend fun updateLimit(limit: AppLimit)
    
    @Delete
    suspend fun deleteLimit(limit: AppLimit)
    
    @Query("DELETE FROM app_limits WHERE packageName = :packageName")
    suspend fun deleteLimitByPackage(packageName: String)
}
