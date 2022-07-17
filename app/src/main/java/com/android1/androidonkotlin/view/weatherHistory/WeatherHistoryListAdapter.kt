package com.android1.androidonkotlin.view.weatherHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android1.androidonkotlin.databinding.FragmentWeatherHistoryListRecyclerItemBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.view.details.OnItemClick


class WeatherHistoryListAdapter(private val dataList:List<WeatherItem>, private val callback: OnItemClick):RecyclerView.Adapter<WeatherHistoryListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding= FragmentWeatherHistoryListRecyclerItemBinding.inflate(LayoutInflater.from(parent.context))
        return WeatherViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class WeatherViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(weather: WeatherItem){
            val binding= FragmentWeatherHistoryListRecyclerItemBinding.bind(itemView)
            binding.cityName.text = weather.city?.name
            binding.temperatureValue.text = weather.temperature.toString()
            binding.root.setOnClickListener {
                callback.onItemClick(weather)
            }
        }
    }
}