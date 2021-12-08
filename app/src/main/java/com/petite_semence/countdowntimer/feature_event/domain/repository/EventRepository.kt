package com.petite_semence.countdowntimer.feature_event.domain.repository

import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEvents(): Flow<List<Event>>

    suspend fun getEventById(id: Long): Event?

    suspend fun insertEvent(event: Event)

    suspend fun deleteEvent(event: Event)
}