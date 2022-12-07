package com.example.asteroidreader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asteroidreader.model.Asteroid

@Database(entities = [Asteroid::class], version = 1 , exportSchema = false)
abstract class AsteroidDB : RoomDatabase() {
    abstract fun  asteroidDao () : AsteroidDao

    companion object{
        @Volatile
        private var INSTANCE : AsteroidDB? = null

        fun getDatabase (context : Context) : AsteroidDB {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDB::class.java,
                    "asteroid_database")

                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}