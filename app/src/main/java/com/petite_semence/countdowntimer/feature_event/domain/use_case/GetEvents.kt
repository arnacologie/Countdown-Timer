package com.petite_semence.countdowntimer.feature_event.domain.use_case

import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEvents(
    private val repository: EventRepository
){
    operator fun invoke() : Flow<List<Event>> {
        return repository.getEvents()
    }
}