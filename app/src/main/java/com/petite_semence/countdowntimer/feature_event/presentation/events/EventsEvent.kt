package com.petite_semence.countdowntimer.feature_event.presentation.events

import com.petite_semence.countdowntimer.feature_event.domain.model.Event

sealed class EventsEvent {
    data class DeleteEvent(val event: Event) : EventsEvent()
    object RestoreNote: EventsEvent()
}