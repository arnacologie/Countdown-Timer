package com.petite_semence.countdowntimer.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.petite_semence.countdowntimer.data.dao.EventDao
import com.petite_semence.countdowntimer.data.entities.Event

@Database(
    entities = [Event::class],
    version = 1,
    exportSchema = false)
abstract class EventDatabase: RoomDatabase(){

    abstract fun eventDao() : EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}