package com.example.amazonbooks

import android.app.Application
import com.example.amazonbooks.di.components.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }
}
