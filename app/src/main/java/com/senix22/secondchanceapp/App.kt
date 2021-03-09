package com.senix22.secondchanceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senix22.secondchanceapp.di.AppComponent
import com.senix22.secondchanceapp.di.AppModule
import com.senix22.secondchanceapp.di.DaggerAppComponent

class App : AppCompatActivity() {
    lateinit var daggerComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        daggerComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}