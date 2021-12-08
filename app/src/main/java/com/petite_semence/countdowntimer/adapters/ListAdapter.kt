package com.petite_semence.countdowntimer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.petite_semence.countdowntimer.data.entities.Event
import com.petite_semence.countdowntimer.databinding.CustomRowBinding
import com.petite_semence.countdowntimer.ui.fragments.ListFragmentDirections
import java.sql.Date
import java.text.SimpleDateFormat

class ListAdapter :RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

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
            binding.tvEventName.text = currentItem.name
            val date =  Date(currentItem.date)
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val formattedDate = format.format(date)
            binding.tvDate.text = formattedDate
            binding.rowLayout.setOnClickListener { view ->
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }

    fun setData(eventList : List<Event>){
        this.eventList = eventList
        notifyDataSetChanged()
    }


}