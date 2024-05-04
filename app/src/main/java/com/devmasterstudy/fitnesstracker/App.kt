package com.devmasterstudy.fitnesstracker

import android.app.Application
import com.devmasterstudy.fitnesstracker.model.AppDataBase

class App : Application() {

    lateinit var db: AppDataBase

    override fun onCreate() {
        super.onCreate()
        db = AppDataBase.getDatabase(this)
    }
}