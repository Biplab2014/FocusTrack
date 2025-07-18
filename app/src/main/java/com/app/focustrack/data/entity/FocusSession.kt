package com.app.focustrack.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long?,
    val duration: Long, // in milliseconds
    val blockedApps: String, // JSON string of blocked app package names
    val isCompleted: Boolean = false,
    val date: String // Format: yyyy-MM-dd
)
