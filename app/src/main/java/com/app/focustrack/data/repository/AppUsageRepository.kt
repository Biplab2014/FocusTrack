package com.app.focustrack.data.repository

import com.app.focustrack.data.dao.AppUsageDao
import com.app.focustrack.data.entity.AppUsage
import kotlinx.coroutines.flow.Flow

class AppUsageRepository(
    private val appUsageDao: AppUsageDao
) {
    
    fun getUsageByDate(date: String): Flow<List<AppUsage>> = 
        appUsageDao.getUsageByDate(date)
    
    fun getUsageBetweenDates(startDate: String, endDate: String): Flow<List<AppUsage>> = 
        appUsageDao.getUsageBetweenDates(startDate, endDate)
    
    fun getTopUsageByDate(date: String, limit: Int = 3): Flow<List<AppUsage>> = 
        appUsageDao.getTopUsageByDate(date, limit)
    
    fun getTotalScreenTimeByDate(date: String): Flow<Long?> = 
        appUsageDao.getTotalScreenTimeByDate(date)
    
    fun getTotalLaunchCountByDate(date: String): Flow<Int?> = 
        appUsageDao.getTotalLaunchCountByDate(date)
    
    fun getAppUsageHistory(packageName: String, startDate: String, endDate: String): Flow<List<AppUsage>> = 
        appUsageDao.getAppUsageHistory(packageName, startDate, endDate)
    
    suspend fun insertUsage(usage: AppUsage) = 
        appUsageDao.insertUsage(usage)
    
    suspend fun insertUsages(usages: List<AppUsage>) = 
        appUsageDao.insertUsages(usages)
    
    suspend fun updateUsage(usage: AppUsage) = 
        appUsageDao.updateUsage(usage)
    
    suspend fun deleteUsage(usage: AppUsage) = 
        appUsageDao.deleteUsage(usage)
    
    suspend fun deleteOldUsage(date: String) = 
        appUsageDao.deleteOldUsage(date)
}
