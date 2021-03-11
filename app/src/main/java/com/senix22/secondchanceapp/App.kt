package com.senix22.secondchanceapp

import android.app.Application

import com.senix22.secondchanceapp.di.AppComponent
import com.senix22.secondchanceapp.di.AppModule
import com.senix22.secondchanceapp.di.DaggerAppComponent

class App : Application() {

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}