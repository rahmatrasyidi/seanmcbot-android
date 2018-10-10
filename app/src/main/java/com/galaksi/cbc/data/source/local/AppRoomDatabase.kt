package com.galaksi.cbc.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.support.annotation.VisibleForTesting
import com.galaksi.cbc.App

/**
 * Created by rahmatrasyidi on 12/08/18.
 */

/*
@Database(entities = [UserDao::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null

        private val lock = Any()

        fun getInstance(): AppRoomDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(App.INSTANCE, AppRoomDatabase::class.java)
                            .allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}*/
