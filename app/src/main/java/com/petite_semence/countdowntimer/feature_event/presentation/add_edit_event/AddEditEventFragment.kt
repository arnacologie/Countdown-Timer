package com.petite_semence.countdowntimer.feature_event.presentation.add_edit_event

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.petite_semence.countdowntimer.databinding.FragmentAddEditEventBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditEventFragment : Fragment() {

    private val viewModel: AddEditEventViewModel by viewModels()
    private var _binding: FragmentAddEditEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job: Job? = null

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.state.collectLatest { state ->
                        binding.etEventTitle.setText(state.title)
                        binding.tvDate.text = state.stringDate
                        binding.tvTime.text = state.stringTime
                    }
                }
                launch {
                    viewModel.eventFlow.collectLatest { event ->
                        when(event) {
                            is AddEditEventViewModel.UiEvent.SaveEvent -> {
                                findNavController().navigateUp()
                            }
                            is AddEditEventViewModel.UiEvent.ShowSnackbar -> {
                                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }

        binding.apply {
            etEventTitle.addTextChangedListener { editable ->
                    job?.cancel()
                    job = lifecycleScope.launch {
                        delay(500L)
                        editable?.let{
                            Log.d("result", it.toString())
                            viewModel.onEvent(AddEditEventEvent.EnteredTitle(it.toString()))
                        }
                    }

            }
            btnPickDate.setOnClickListener {

                val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                        _, year, month, day ->
                    viewModel.onEvent(AddEditEventEvent.EnteredDate(day, month, year))
                }, viewModel.initYear, viewModel.initMonth, viewModel.initDay)

                datePickerDialog.show()
            }
            btnPickTime.setOnClickListener {
                val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
                val clockFormat = if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

                val picker = MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(viewModel.initHour)
                    .setMinute(viewModel.initMinute)
                    .setTitleText("Set Time Event")
                    .build()

                picker.addOnPositiveButtonClickListener {
                    viewModel.onEvent(AddEditEventEvent.EnteredTime(picker.hour, picker.minute))
                }

                picker.show(parentFragmentManager, "TAG")
            }
            btnStart.setOnClickListener {
                viewModel.onEvent(AddEditEventEvent.SaveEvent)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

