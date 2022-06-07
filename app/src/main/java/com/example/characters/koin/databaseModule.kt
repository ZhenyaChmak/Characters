package com.example.characters.koin

import androidx.room.Room
import com.example.characters.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room
            .databaseBuilder(
                get(),
                AppDatabase::class.java,
                "app-database"
            )
            .build()
    }

    single {
        get<AppDatabase>().userDao()
    }
}