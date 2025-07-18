package com.app.focustrack.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_limits")
data class AppLimit(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val dailyLimitMinutes: Int,
    val isEnabled: Boolean = true
)
