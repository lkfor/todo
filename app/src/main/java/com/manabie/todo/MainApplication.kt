package com.manabie.todo

import android.app.Application
import com.manabie.todo.db.AppDatabase

//import com.amitshekhar.DebugDB;
class MainApplication : Application() {
    private val sAppDatabase: AppDatabase? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        mAppExecutors = AppExecutors()
    }

    private fun getDatabase(): AppDatabase {
        return AppDatabase.getInstance(instance, mAppExecutors)
    }

    fun getDataRepository(): DataRepository {
        return DataRepository.getInstance(getDatabase())
    }

    companion object {
        private var mAppExecutors: AppExecutors? = null
        private val database: AppDatabase
            get() = AppDatabase.getInstance(
                instance,
                mAppExecutors
            )

        var instance: MainApplication? = null
    }
}