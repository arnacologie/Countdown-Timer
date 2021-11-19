package com.petite_semence.countdowntimer

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.petite_semence.countdowntimer.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)

        binding.apply {
            setDate(day, month, year)
            setTime(hour, min)
            btnPickDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener{
                        _, mYear, mMonth, mDay ->
                    setDate(mDay, mMonth, mYear)
                }, year, month, day)

                datePickerDialog.show()
            }
            btnPickTime.setOnClickListener {
                val isSystem24Hour = is24HourFormat(this@MainActivity)
                val clockFormat = if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

                val picker = MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Set Time Event")
                    .build()

                picker.addOnPositiveButtonClickListener { setTime(picker.hour, picker.minute) }

                picker.show(supportFragmentManager, "TAG")
            }

        }

    }
    private fun setDate(day:Int, month: Int, year:Int){
        val formattedDay = String.format("%02d",day)
        val formattedMonth = String.format("%02d", month+1)
        binding.tvDate.text = "${formattedDay}/${formattedMonth}/$year"
    }

    private fun setTime(hour:Int, minute:Int){
        val formattedHour = String.format("%02d",hour).also(::println)
        val formattedMinute = String.format("%02d", minute).also(::println)
        binding.tvTime.text = "${formattedHour}:${formattedMinute}"
    }

}