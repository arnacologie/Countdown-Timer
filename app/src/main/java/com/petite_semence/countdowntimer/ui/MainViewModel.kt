package com.petite_semence.countdowntimer.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petite_semence.countdowntimer.data.EventDatabase
import com.petite_semence.countdowntimer.data.EventRepository
import com.petite_semence.countdowntimer.data.entities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val applicationContext: Context) : ViewModel() {

    val readAllData: LiveData<List<Event>>
    private val repository: EventRepository

    init {
        val eventDao = EventDatabase.getDatabase(applicationContext).eventDao()
        repository = EventRepository(eventDao)
        readAllData = repository.readAllData
    }

    fun addEvent(event: Event){
        viewModelScope.launch(Dispatchers.IO){
            repository.addEvent(event)
        }
    }

}