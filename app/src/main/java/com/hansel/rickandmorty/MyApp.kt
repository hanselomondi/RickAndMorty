package com.hansel.rickandmorty

import android.app.Application
import com.hansel.rickandmorty.di.apiServiceModule
import com.hansel.rickandmorty.di.dataSourceModule
import com.hansel.rickandmorty.di.okHttpModule
import com.hansel.rickandmorty.di.repositoryModule
import com.hansel.rickandmorty.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                okHttpModule + apiServiceModule + dataSourceModule + repositoryModule + viewModelModule
            )
        }
    }
}