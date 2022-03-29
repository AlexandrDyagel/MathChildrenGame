package com.xelar.legayd.congratulationapp.di

import com.xelar.legayd.congratulationapp.presentation.ContainerActivity
import dagger.Component

@Component(modules = [ContextModule::class])
interface AppComponent {

    fun inject(activity: ContainerActivity)
}