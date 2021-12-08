package com.petite_semence.countdowntimer.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petite_semence.countdowntimer.data.entities.Event

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addEvent(event: Event)

    @Query("SELECT * FROM event_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Event>>
}