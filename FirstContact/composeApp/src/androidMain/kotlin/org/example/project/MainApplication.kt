package org.example.project

import android.app.Application
import com.expenseApp.db.Database
import data.DatabaseDriverFactory
import dependencyInjection.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule(Database.invoke(DatabaseDriverFactory(this@MainApplication).createDriver())))
        }
    }
}