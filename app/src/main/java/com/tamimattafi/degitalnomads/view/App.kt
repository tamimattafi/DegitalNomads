package com.tamimattafi.degitalnomads.view

import android.app.Application
import com.tamimattafi.degitalnomads.model.database.NewsDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NewsDatabase.initDatabase(this)
    }
}

