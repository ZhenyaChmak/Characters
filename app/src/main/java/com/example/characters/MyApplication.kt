package com.example.characters

import android.app.Application
import com.example.characters.data.koin.dataModule
import com.example.characters.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                dataModule,
                userDetailsViewModelModule,
                userViewModelModule
            )
        }
    }

}

