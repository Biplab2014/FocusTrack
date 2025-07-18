package com.app.focustrack.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_usage")
data class AppUsage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val totalTimeInForeground: Long, // in milliseconds
    val launchCount: Int,
    val date: String, // Format: yyyy-MM-dd
    val timestamp: Long = System.currentTimeMillis()
)
