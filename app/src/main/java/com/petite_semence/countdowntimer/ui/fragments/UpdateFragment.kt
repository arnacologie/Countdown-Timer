package com.petite_semence.countdowntimer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.petite_semence.countdowntimer.databinding.FragmentUpdateBinding
import com.petite_semence.countdowntimer.ui.MainViewModel
import java.sql.Date
import java.text.SimpleDateFormat

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        val view = binding.root

        with(binding){
            etUpdateEventName.setText(args.currentEvent.name)
            val date =  Date(args.currentEvent.date)
            val format = SimpleDateFormat("dd/MM/yyyy")
            val formattedDate = format.format(date)
            tvUpdateDate.text = formattedDate
            val formatTime = SimpleDateFormat("HH:mm")
            val formattedTime = format.format(date)
            tvUpdateTime.text = formattedTime
        }
        return view
    }
    
}