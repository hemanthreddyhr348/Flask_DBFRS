package com.binatebits.food_reccomendation_system.application

import android.app.Application
import com.binatebits.food_reccomendation_system.util.Prefs
import dagger.hilt.android.HiltAndroidApp

val prefs: Prefs by lazy {
    App.prefs!!
}

@HiltAndroidApp
class App : Application() {

    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)

    }
}