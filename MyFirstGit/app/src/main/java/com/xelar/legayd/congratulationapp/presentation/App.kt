package com.xelar.legayd.congratulationapp.presentation

import android.app.Application
import android.content.Context
import com.xelar.legayd.congratulationapp.di.AppComponent
import com.xelar.legayd.congratulationapp.di.ContextModule
import com.xelar.legayd.congratulationapp.di.DaggerAppComponent


class App() : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }