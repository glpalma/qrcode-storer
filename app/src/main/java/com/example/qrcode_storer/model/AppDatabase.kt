package com.example.qrcode_storer.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Code::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun codeDao(): CodeDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "qrcode_mlkit_database"
                ).build()
                .also { Instance = it }
            }
        }
    }
}
