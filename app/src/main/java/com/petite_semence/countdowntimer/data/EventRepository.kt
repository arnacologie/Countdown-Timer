package com.petite_semence.countdowntimer.data

import androidx.lifecycle.LiveData
import com.petite_semence.countdowntimer.data.dao.EventDao
import com.petite_semence.countdowntimer.data.entities.Event

class EventRepository(private val eventDao: EventDao) {

    val readAllData: LiveData<List<Event>> = eventDao.readAllData()

    suspend fun addEvent(event:Event){
        eventDao.addEvent(event)
    }

}