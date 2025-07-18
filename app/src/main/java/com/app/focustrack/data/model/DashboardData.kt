package com.app.focustrack.data.model

import com.app.focustrack.data.entity.AppUsage

data class DashboardData(
    val totalScreenTime: Long = 0L, // in milliseconds
    val topApps: List<AppUsage> = emptyList(),
    val screenUnlocks: Int = 0,
    val focusSessionsCompleted: Int = 0,
    val isFocusModeActive: Boolean = false
)

data class AppUsageWithIcon(
    val appUsage: AppUsage,
    val appIcon: android.graphics.drawable.Drawable? = null
)

data class UsageStats(
    val packageName: String,
    val appName: String,
    val totalTimeInForeground: Long,
    val launchCount: Int,
    val appIcon: android.graphics.drawable.Drawable? = null
)

data class WeeklyReport(
    val weekStartDate: String,
    val weekEndDate: String,
    val totalScreenTime: Long,
    val averageDailyScreenTime: Long,
    val topApps: List<AppUsage>,
    val screenUnlocks: Int,
    val focusSessionsCompleted: Int,
    val dailyUsage: List<DailyUsage>
)

data class DailyUsage(
    val date: String,
    val totalScreenTime: Long,
    val screenUnlocks: Int,
    val focusSessionsCompleted: Int
)
