package com.petite_semence.countdowntimer.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.petite_semence.countdowntimer.R
import com.petite_semence.countdowntimer.adapters.ListAdapter
import com.petite_semence.countdowntimer.databinding.FragmentListBinding
import com.petite_semence.countdowntimer.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import java.time.LocalDate

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater)

        //RecyclerView
        val listAdapter = ListAdapter()
        val recyclerView = binding.rvEventList
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        //ViewModel
        viewModel.readAllData.observe(viewLifecycleOwner, Observer { eventList ->
            listAdapter.setData(eventList)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addEventFragment)
        }

        return binding.root
    }
}