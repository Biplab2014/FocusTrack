package com.app.focustrack.di

import android.content.Context
import com.app.focustrack.data.database.FocusTrackDatabase
import com.app.focustrack.data.repository.AppUsageRepository
import com.app.focustrack.data.repository.FocusSessionRepository
import com.app.focustrack.service.UsageStatsService
import com.app.focustrack.ui.viewmodel.DashboardViewModel

class AppContainer(private val context: Context) {
    
    // Database
    private val database by lazy {
        FocusTrackDatabase.getDatabase(context)
    }
    
    // DAOs
    private val appUsageDao by lazy { database.appUsageDao() }
    private val focusSessionDao by lazy { database.focusSessionDao() }
    private val screenUnlockDao by lazy { database.screenUnlockDao() }
    private val appLimitDao by lazy { database.appLimitDao() }
    
    // Services
    val usageStatsService by lazy { UsageStatsService(context) }
    
    // Repositories
    val appUsageRepository by lazy { AppUsageRepository(appUsageDao) }
    val focusSessionRepository by lazy { FocusSessionRepository(focusSessionDao) }
    
    // ViewModels
    val dashboardViewModel by lazy {
        DashboardViewModel(
            appUsageRepository = appUsageRepository,
            focusSessionRepository = focusSessionRepository,
            usageStatsService = usageStatsService
        )
    }
}
