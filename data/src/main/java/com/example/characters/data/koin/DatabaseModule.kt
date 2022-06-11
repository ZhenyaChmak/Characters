package com.example.characters.data.koin

import androidx.room.Room
import com.example.characters.data.database.AppDatabase
import org.koin.dsl.module

internal val databaseModule = module {

    single {
        Room
            .databaseBuilder(
                get(),
                AppDatabase::class.java,
                "database"
            )
            .build()
    }

    single {
        get<AppDatabase>().userDao()
    }

}