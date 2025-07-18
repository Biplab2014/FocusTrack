package com.app.focustrack.data.dao

import androidx.room.*
import com.app.focustrack.data.entity.FocusSession
import kotlinx.coroutines.flow.Flow

@Dao
interface FocusSessionDao {
    
    @Query("SELECT * FROM focus_sessions WHERE date = :date ORDER BY startTime DESC")
    fun getSessionsByDate(date: String): Flow<List<FocusSession>>
    
    @Query("SELECT * FROM focus_sessions WHERE date BETWEEN :startDate AND :endDate ORDER BY startTime DESC")
    fun getSessionsBetweenDates(startDate: String, endDate: String): Flow<List<FocusSession>>
    
    @Query("SELECT * FROM focus_sessions WHERE endTime IS NULL LIMIT 1")
    fun getActiveFocusSession(): Flow<FocusSession?>
    
    @Query("SELECT COUNT(*) FROM focus_sessions WHERE date = :date AND isCompleted = 1")
    fun getCompletedSessionsCountByDate(date: String): Flow<Int>
    
    @Query("SELECT SUM(duration) FROM focus_sessions WHERE date = :date AND isCompleted = 1")
    fun getTotalFocusTimeByDate(date: String): Flow<Long?>
    
    @Insert
    suspend fun insertSession(session: FocusSession): Long
    
    @Update
    suspend fun updateSession(session: FocusSession)
    
    @Delete
    suspend fun deleteSession(session: FocusSession)
    
    @Query("DELETE FROM focus_sessions WHERE date < :date")
    suspend fun deleteOldSessions(date: String)
}
