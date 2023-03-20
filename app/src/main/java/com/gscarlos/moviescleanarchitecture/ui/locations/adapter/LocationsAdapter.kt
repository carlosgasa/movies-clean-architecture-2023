package com.gscarlos.moviescleanarchitecture.ui.locations.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.databinding.ItemLocationBinding
import com.gscarlos.moviescleanarchitecture.data.remote.firebase.model.LocationDto
import com.gscarlos.moviescleanarchitecture.domain.model.LocationToShow


class LocationsAdapter(
    private var locations: List<LocationToShow> = emptyList(),
    private val listener: (LocationToShow) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(
            layoutInflater.inflate(
                R.layout.item_location,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locations[position])
        holder.binding.cvItem.setOnClickListener { listener(locations[position]) }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setList(list: List<LocationToShow>) {
        locations = list
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLocationBinding.bind(view)

        fun bind(location: LocationToShow) {
            binding.tvDate.text = location.fecha.toString()
            binding.tvLatitude.text = location.latitud.toString()
            binding.tvLongitude.text = location.longitud.toString()
        }

    }
}