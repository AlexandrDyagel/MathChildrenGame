package com.xelar.legayd.congratulationapp.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val application: Context) {

    @Provides
    fun provideContext(): Context {
        return application
    }
}