package com.petite_semence.countdowntimer.feature_event.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.petite_semence.countdowntimer.databinding.CustomRowBinding
import com.petite_semence.countdowntimer.feature_event.domain.model.Event
import com.petite_semence.countdowntimer.feature_event.domain.util.DateFormatUtil
import com.petite_semence.countdowntimer.feature_event.presentation.events.EventsFragmentDirections

class EventAdapter :RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    private var eventList = emptyList<Event>()

    class MyViewHolder(val binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = eventList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        with(holder){
            binding.tvEventName.text = currentItem.title
            binding.tvDate.text = DateFormatUtil.timestampToString(currentItem.timestamp)
            binding.rowLayout.setOnClickListener { view ->
                val action = EventsFragmentDirections.actionEventsFragmentToAddEditEventFragment(eventId = currentItem.id)
                view.findNavController().navigate(action)
            }
        }
    }

    fun setData(eventList : List<Event>){
        this.eventList = eventList
        notifyDataSetChanged()
    }


}