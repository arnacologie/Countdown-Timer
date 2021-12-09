package com.petite_semence.countdowntimer.feature_event.domain.use_case

import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.model.InvalidEventException
import com.petite_semence.countdowntimer.feature_event.domain.repository.EventRepository

class AddEvent(
    private val repository: EventRepository
){
    @Throws(InvalidEventException::class)
    suspend operator fun invoke(event: Event){
        if(event.title.isBlank()){
            throw InvalidEventException("Le titre ne peut être vide")
        }
        repository.insertEvent(event)
    }
}