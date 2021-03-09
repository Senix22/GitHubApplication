package com.senix22.secondchanceapp.di

import com.senix22.secondchanceapp.ui.NavigationActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: NavigationActivity)

}