package com.petite_semence.countdowntimer.feature_event.presentation.add_edit_event

import com.petite_semence.countdowntimer.feature_event.domain.util.DateFormatUtil

data class AddEditEventState(
    val title : String = "",
    val stringDate : String = DateFormatUtil.timestampToDateString(System.currentTimeMillis()),
    val stringTime : String = DateFormatUtil.timestampToTimeString(System.currentTimeMillis())
)