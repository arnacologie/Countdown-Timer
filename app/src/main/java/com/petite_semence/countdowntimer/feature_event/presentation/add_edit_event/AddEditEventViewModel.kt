package com.petite_semence.countdowntimer.feature_event.presentation.add_edit_event

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.model.InvalidEventException
import com.petite_semence.countdowntimer.feature_event.domain.use_case.EventUseCases
import com.petite_semence.countdowntimer.feature_event.domain.util.DateFormatUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentEventId : Long = 0L
    val c = Calendar.getInstance()
    val initYear = c.get(Calendar.YEAR)
    val initMonth = c.get(Calendar.MONTH)
    val initDay = c.get(Calendar.DAY_OF_MONTH)
    val initHour = c.get(Calendar.HOUR_OF_DAY)
    val initMinute = c.get(Calendar.MINUTE)

    init {
        savedStateHandle.get<Long>("eventId")?.let { eventId ->
            if(eventId != 0L){
                viewModelScope.launch {
                    eventUseCases.getEvent(eventId)?.also { event ->
                        currentEventId = event.id
                        _state.value = _state.value.copy(
                            title = event.title,
                            stringDate = DateFormatUtil.timestampToDateString(event.timestamp),
                            stringTime =  DateFormatUtil.timestampToTimeString(event.timestamp)
                        )
                    }
                }
            }
        }
    }

    private val _state = MutableStateFlow<AddEditEventState>(AddEditEventState())
    val state : StateFlow<AddEditEventState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow : SharedFlow<UiEvent> = _eventFlow

    fun onEvent(event: AddEditEventEvent){
        when(event){
            is AddEditEventEvent.EnteredTitle -> {
                _state.value = _state.value.copy(
                    title = event.title
                )
            }
            is AddEditEventEvent.EnteredDate -> {
                _state.value = _state.value.copy(
                    stringDate = DateFormatUtil.intsToDateString(event.day, event.month, event.year)
                )
            }
            is AddEditEventEvent.EnteredTime -> {
                _state.value = _state.value.copy(
                    stringTime = DateFormatUtil.intsToTimeString(event.hour, event.minute)
                )
            }

            is AddEditEventEvent.SaveEvent -> {
                viewModelScope.launch {
                    try {
                        val eventLongDate = DateFormatUtil.stringToTimestamp(
                            _state.value.stringDate,
                            _state.value.stringTime
                        )
                        eventUseCases.addEvent(
                            Event(
                                title = _state.value.title,
                                timestamp = eventLongDate,
                                id = currentEventId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveEvent)
                    } catch (e: InvalidEventException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                e.message ?: "Sauvegarde impossible"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveEvent : UiEvent()
    }

}