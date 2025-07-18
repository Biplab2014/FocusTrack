package com.app.focustrack.data.repository

import com.app.focustrack.data.dao.FocusSessionDao
import com.app.focustrack.data.entity.FocusSession
import kotlinx.coroutines.flow.Flow

class FocusSessionRepository(
    private val focusSessionDao: FocusSessionDao
) {
    
    fun getSessionsByDate(date: String): Flow<List<FocusSession>> = 
        focusSessionDao.getSessionsByDate(date)
    
    fun getSessionsBetweenDates(startDate: String, endDate: String): Flow<List<FocusSession>> = 
        focusSessionDao.getSessionsBetweenDates(startDate, endDate)
    
    fun getActiveFocusSession(): Flow<FocusSession?> = 
        focusSessionDao.getActiveFocusSession()
    
    fun getCompletedSessionsCountByDate(date: String): Flow<Int> = 
        focusSessionDao.getCompletedSessionsCountByDate(date)
    
    fun getTotalFocusTimeByDate(date: String): Flow<Long?> = 
        focusSessionDao.getTotalFocusTimeByDate(date)
    
    suspend fun insertSession(session: FocusSession): Long = 
        focusSessionDao.insertSession(session)
    
    suspend fun updateSession(session: FocusSession) = 
        focusSessionDao.updateSession(session)
    
    suspend fun deleteSession(session: FocusSession) = 
        focusSessionDao.deleteSession(session)
    
    suspend fun deleteOldSessions(date: String) = 
        focusSessionDao.deleteOldSessions(date)
}
