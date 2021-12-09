package com.petite_semence.countdowntimer.feature_event.domain.util

import com.petite_semence.countdowntimer.feature_event.domain.model.InvalidEventException
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {

    fun timestampToDateString(timestamp: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run {
            format(timestamp)
        }
    }
    fun timestampToTimeString(timestamp: Long): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).run {
            format(timestamp)
        }
    }
    fun intsToDateString(day: Int, month:Int, year:Int): String {
        val formattedDay = String.format("%02d",day)
        val formattedMonth = String.format("%02d", month+1)
        return "${formattedDay}/${formattedMonth}/$year"
    }
    fun intsToTimeString(hour: Int, minute:Int): String {
        val formattedHour = String.format("%02d",hour)
        val formattedMinute = String.format("%02d", minute)
        return "${formattedHour}:${formattedMinute}"
    }

    @Throws(InvalidEventException::class)
    fun stringToTimestamp(dateString:String, timeString: String) : Long {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).run {
            parse("$dateString $timeString")?: throw InvalidEventException("Erreur conversion date -> timestamp")
        }
        return date.time
    }

    fun timestampToString(timestamp: Long) : String {
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).run {
            format(timestamp)
        }
    }
}