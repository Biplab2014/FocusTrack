package com.app.focustrack.service

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import com.app.focustrack.data.entity.AppUsage
import com.app.focustrack.data.model.UsageStats as AppUsageStats
import java.text.SimpleDateFormat
import java.util.*

class UsageStatsService(
    private val context: Context
) {
    
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    private val packageManager = context.packageManager
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    fun hasUsageStatsPermission(): Boolean {
        return try {
            val currentTime = System.currentTimeMillis()
            val stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                currentTime - 1000,
                currentTime
            )
            stats != null && stats.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
    
    fun getTodayUsageStats(): List<AppUsageStats> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        val endTime = System.currentTimeMillis()
        
        return getUsageStats(startTime, endTime)
    }
    
    fun getUsageStats(startTime: Long, endTime: Long): List<AppUsageStats> {
        return try {
            val stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime
            )

            stats?.mapNotNull { usageStats ->
                try {
                    if (usageStats.totalTimeInForeground > 0) {
                        val appName = getAppName(usageStats.packageName)
                        val appIcon = getAppIcon(usageStats.packageName)

                        AppUsageStats(
                            packageName = usageStats.packageName,
                            appName = appName,
                            totalTimeInForeground = usageStats.totalTimeInForeground,
                            launchCount = usageStats.lastTimeUsed.toInt(), // This is a simplified approach
                            appIcon = appIcon
                        )
                    } else null
                } catch (e: Exception) {
                    null // Skip problematic entries
                }
            }?.sortedByDescending { it.totalTimeInForeground } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun convertToAppUsage(usageStats: List<AppUsageStats>, date: String): List<AppUsage> {
        return usageStats.map { stats ->
            AppUsage(
                packageName = stats.packageName,
                appName = stats.appName,
                totalTimeInForeground = stats.totalTimeInForeground,
                launchCount = stats.launchCount,
                date = date
            )
        }
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            packageName
        }
    }
    
    private fun getAppIcon(packageName: String): android.graphics.drawable.Drawable? {
        return try {
            packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
    
    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }
}
