package com.petite_semence.countdowntimer.feature_event.presentation.add_edit_event

sealed class AddEditEventEvent {
    data class EnteredTitle(val title: String): AddEditEventEvent()
    data class EnteredDate(val day: Int, val month:Int, val year:Int) : AddEditEventEvent()
    data class EnteredTime(val hour: Int, val minute:Int) : AddEditEventEvent()
    object SaveEvent: AddEditEventEvent()
}