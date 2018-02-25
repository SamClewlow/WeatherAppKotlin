package uk.co.pdextech.android.weatherapp

import android.app.Application

/**
 * Created by Pdex on 13/02/2018.
 */
class App: Application() {

    companion object {
        var instance: Application by DelegatesExt.notNullSingleValue<Application>()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}