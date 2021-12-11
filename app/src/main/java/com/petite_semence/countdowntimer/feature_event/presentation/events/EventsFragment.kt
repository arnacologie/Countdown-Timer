package com.petite_semence.countdowntimer.feature_event.presentation.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.petite_semence.countdowntimer.R
import com.petite_semence.countdowntimer.databinding.FragmentEventsBinding
import com.petite_semence.countdowntimer.feature_event.presentation.adapters.EventAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView
        val eventAdapter = EventAdapter()
        eventAdapter.setOnLongClickListener { event ->
            viewModel.onEvent(EventsEvent.DeleteEvent(event))
            Snackbar.make(binding.root, "Évènement supprimé", Snackbar.LENGTH_LONG)
                .setAction("Annuler") { viewModel.onEvent(EventsEvent.RestoreNote) }
                .show()
        }
        val recyclerView = binding.rvEvents
        recyclerView.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        //ViewModel
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { eventState ->
                eventAdapter.setData(eventState.events)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_eventsFragment_to_addEditEventFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}