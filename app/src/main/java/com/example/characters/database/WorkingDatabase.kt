package com.example.characters.database

import android.app.Application
import android.content.Context
import androidx.room.Room

class WorkingDatabase : Application() {

    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = requireNotNull(_appDatabase)

    override fun onCreate() {
        super.onCreate()

        _appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app-database"
        )
            .build()
    }

}

val Context.appDatabase: AppDatabase
    get() = when (this) {
        is WorkingDatabase -> appDatabase
        else -> applicationContext.appDatabase
    }
