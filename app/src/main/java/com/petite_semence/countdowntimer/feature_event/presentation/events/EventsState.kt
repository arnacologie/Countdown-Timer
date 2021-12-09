package com.petite_semence.countdowntimer.feature_event.presentation.events

import com.petite_semence.countdowntimer.feature_event.domain.model.Event

data class EventsState(
    val events: List<Event> = emptyList()
)
