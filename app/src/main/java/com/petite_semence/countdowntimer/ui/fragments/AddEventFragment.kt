package com.petite_semence.countdowntimer.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.petite_semence.countdowntimer.R
import com.petite_semence.countdowntimer.data.entities.Event
import com.petite_semence.countdowntimer.databinding.ActivityMainBinding
import com.petite_semence.countdowntimer.databinding.FragmentAddEventBinding
import com.petite_semence.countdowntimer.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddEventFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEventBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                        _, mYear, mMonth, mDay ->
                    setDate(mDay, mMonth, mYear)
                }, year, month, day)

                datePickerDialog.show()
            }
            btnPickTime.setOnClickListener {
                val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
                val clockFormat = if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

                val picker = MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Set Time Event")
                    .build()

                picker.addOnPositiveButtonClickListener { setTime(picker.hour, picker.minute) }

                picker.show(parentFragmentManager, "TAG")
            }
            btnStart.setOnClickListener {
                insertDataToDatabase()
            }


        }
    }

    private fun insertDataToDatabase() {
        with(binding){
        val eventName = etEventName.text.toString()
        var dateLong = 0L
            try {
               dateLong = SimpleDateFormat("dd/MM/yyyy HH:mm")
                    .parse("${tvDate.text} ${tvTime.text}")
                    .time

                if(inputCheck(eventName, dateLong)){
                    val event = Event(0, eventName, dateLong)
                    viewModel.addEvent(event)
                    Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_addEventFragment_to_listFragment)
                }  else{
                    Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
                }

            }catch(e:Exception) {
                Log.d("ListFragment","Date wasn't parse correctly")
                Toast.makeText(requireContext(), "Date is invalid", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun inputCheck(eventName: String, dateLong: Long) : Boolean {
        return !(TextUtils.isEmpty(eventName) && (dateLong == 0L))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

