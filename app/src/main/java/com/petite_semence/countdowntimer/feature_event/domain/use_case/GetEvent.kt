package com.petite_semence.countdowntimer.feature_event.domain.use_case

import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.repository.EventRepository

class GetEvent(
    private val repository: EventRepository
) {

    suspend operator fun invoke(id: Long): Event? {
        return repository.getEventById(id)
    }
}