package com.petite_semence.countdowntimer.feature_event.data.repository

import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.repository.EventRepository
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.toFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class EventRepositoryImpl(
    private val boxStore: BoxStore
) : EventRepository{

    private val eventBox: Box<Event> = boxStore.boxFor()

    override fun getEvents(): Flow<List<Event>> {
        return eventBox.query().build().subscribe().toFlow()
    }

    override suspend fun getEventById(id: Long): Event? = withContext(Dispatchers.IO) {
         eventBox.get(id)
    }

    override suspend fun insertEvent(event: Event): Unit = withContext(Dispatchers.IO) {
        eventBox.put(event)
    }

    override suspend fun deleteEvent(event: Event): Unit = withContext(Dispatchers.IO){
        eventBox.remove(event)
    }
}