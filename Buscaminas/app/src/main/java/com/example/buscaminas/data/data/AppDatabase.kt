package com.example.buscaminas.data.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PartidaEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun partidaDao(): PartidaDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "buscaminas_db").build()

                INSTANCE = instance

                instance
            }
        }
    }
}