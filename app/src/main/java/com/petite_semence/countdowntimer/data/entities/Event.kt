package com.petite_semence.countdowntimer.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event_table")
data class Event (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val date: Long
) : Parcelable