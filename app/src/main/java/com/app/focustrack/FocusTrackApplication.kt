package com.app.focustrack

import android.app.Application
import com.app.focustrack.data.database.FocusTrackDatabase
import com.app.focustrack.di.AppContainer

class FocusTrackApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        // Close database connections when app terminates
        FocusTrackDatabase.closeDatabase()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // Optionally close database on low memory
        System.gc()
    }
}
