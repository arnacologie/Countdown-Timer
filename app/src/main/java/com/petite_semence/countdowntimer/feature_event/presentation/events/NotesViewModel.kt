package com.petite_semence.countdowntimer.feature_event.presentation.events

import androidx.lifecycle.ViewModel
import com.petite_semence.countdowntimer.feature_event.domain.use_case.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val eventUseCases: EventUseCases
) : ViewModel() {

}