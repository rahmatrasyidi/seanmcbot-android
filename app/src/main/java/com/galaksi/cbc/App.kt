package com.galaksi.cbc

import android.app.Application

/**
 * Created by mobs-rasyidi on 12/08/18.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }
}