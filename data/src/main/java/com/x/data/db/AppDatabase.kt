package com.x.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.x.data.db.dao.AccountDao
import com.x.data.db.entity.Account

@Database(entities = [Account::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val databaseName = "jenciDb"
        private var appDatabase: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase =
                    Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                        .fallbackToDestructiveMigration().build()
            }

            return appDatabase as AppDatabase
        }
    }

    abstract fun accountDao(): AccountDao
}