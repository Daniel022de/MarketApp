package com.devmasterstudy.fitnesstracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Mart::class], version = 3)
@TypeConverters(DateConverter::class)

abstract class AppDataBase : RoomDatabase() {

    abstract fun martDao(): MartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context) : AppDataBase{
            return if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "list_kart"
                       ).fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE as AppDataBase

            } else {
                INSTANCE as AppDataBase
            }

        }
    }
}