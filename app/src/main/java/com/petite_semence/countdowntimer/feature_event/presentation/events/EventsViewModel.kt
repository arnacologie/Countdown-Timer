package com.petite_semence.countdowntimer.feature_event.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.use_case.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventUseCases: EventUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<EventsState>(EventsState())
    val state : StateFlow<EventsState> = _state

    private var recentlyDeletedEvent : Event? = null

    init {
        getEvents()
    }

    fun onEvent(event: EventsEvent){
        when(event){
            is EventsEvent.DeleteEvent -> {
                viewModelScope.launch {
                    eventUseCases.deleteEvent(event.event)
                    recentlyDeletedEvent = event.event
                }
            }
            is EventsEvent.RestoreNote -> {
                viewModelScope.launch {
                    eventUseCases.addEvent(recentlyDeletedEvent ?: return@launch)
                    recentlyDeletedEvent = null
                }

            }
        }
    }

    private fun getEvents(){
        eventUseCases.getEvents()
            .onEach { events ->
                _state.value = _state.value.copy(
                    events = events
                )
            }
            .launchIn(viewModelScope)
    }

}