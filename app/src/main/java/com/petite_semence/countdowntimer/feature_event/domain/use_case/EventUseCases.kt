package com.petite_semence.countdowntimer.feature_event.domain.use_case

data class EventUseCases(
    val getEvents: GetEvents,
    val deleteEvent: DeleteEvent,
    val addEvent: AddEvent,
    val getEvent: GetEvent
)