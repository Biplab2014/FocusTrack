package com.app.focustrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focustrack.data.model.DashboardData
import com.app.focustrack.data.repository.AppUsageRepository
import com.app.focustrack.data.repository.FocusSessionRepository
import com.app.focustrack.service.UsageStatsService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

class DashboardViewModel(
    private val appUsageRepository: AppUsageRepository,
    private val focusSessionRepository: FocusSessionRepository,
    private val usageStatsService: UsageStatsService
) : ViewModel() {
    
    private val _dashboardData = MutableStateFlow(DashboardData())
    val dashboardData: StateFlow<DashboardData> = _dashboardData.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _hasUsagePermission = MutableStateFlow(false)
    val hasUsagePermission: StateFlow<Boolean> = _hasUsagePermission.asStateFlow()

    private var dataCollectionJob: Job? = null

    init {
        checkUsagePermission()
        loadDashboardData()
    }
    
    private fun checkUsagePermission() {
        _hasUsagePermission.value = usageStatsService.hasUsageStatsPermission()
    }
    
    fun loadDashboardData() {
        // Cancel any existing data collection job to prevent resource leaks
        dataCollectionJob?.cancel()

        dataCollectionJob = viewModelScope.launch {
            _isLoading.value = true

            val currentDate = usageStatsService.getCurrentDate()

            try {
                // Combine all the flows to create dashboard data
                combine(
                    appUsageRepository.getTotalScreenTimeByDate(currentDate),
                    appUsageRepository.getTopUsageByDate(currentDate, 3),
                    focusSessionRepository.getCompletedSessionsCountByDate(currentDate),
                    focusSessionRepository.getActiveFocusSession()
                ) { totalScreenTime, topApps, focusSessionsCompleted, activeFocusSession ->

                    DashboardData(
                        totalScreenTime = totalScreenTime ?: 0L,
                        topApps = topApps,
                        screenUnlocks = 0, // Will be implemented with screen unlock tracking
                        focusSessionsCompleted = focusSessionsCompleted,
                        isFocusModeActive = activeFocusSession != null
                    )
                }.collect { data ->
                    _dashboardData.value = data
                    _isLoading.value = false // Set loading to false after first emission
                }

            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
    
    fun refreshUsageData() {
        viewModelScope.launch {
            if (!usageStatsService.hasUsageStatsPermission()) {
                _hasUsagePermission.value = false
                return@launch
            }
            
            try {
                val currentDate = usageStatsService.getCurrentDate()
                val usageStats = usageStatsService.getTodayUsageStats()
                val appUsageList = usageStatsService.convertToAppUsage(usageStats, currentDate)
                
                appUsageRepository.insertUsages(appUsageList)
                
                loadDashboardData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun onPermissionGranted() {
        checkUsagePermission()
        if (_hasUsagePermission.value) {
            refreshUsageData()
        }
    }

    fun onResume() {
        // Re-check permission when app resumes (user might have granted it in settings)
        val hadPermission = _hasUsagePermission.value
        checkUsagePermission()

        // If permission was just granted, refresh data
        if (!hadPermission && _hasUsagePermission.value) {
            refreshUsageData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Cancel any ongoing data collection to prevent resource leaks
        dataCollectionJob?.cancel()
    }
}
