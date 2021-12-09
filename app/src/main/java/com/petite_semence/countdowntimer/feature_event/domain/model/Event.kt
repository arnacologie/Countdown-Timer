package com.petite_semence.countdowntimer.feature_event.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Event (
    @Id
    var id: Long = 0L,
    val title: String = "",
    val timestamp: Long = 0L
)

class InvalidEventException(message: String): Exception(message)