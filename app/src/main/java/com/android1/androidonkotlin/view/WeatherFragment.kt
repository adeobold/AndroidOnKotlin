package com.android1.androidonkotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android1.androidonkotlin.databinding.WeatherFragmentBinding
import com.android1.androidonkotlin.viewmodel.AppState

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel
    lateinit var binding: WeatherFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner,object : Observer<AppState> {
            override fun onChanged(t: AppState) {
                renderData(t)
            }
        })
        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState){
        when (appState){
            is AppState.Error -> {/*TODO HW*/ }
            AppState.Loading -> {/*TODO HW*/}
            is AppState.Success -> {
                val result = appState.weatherData
//                binding.city.text = result.city.name
//                binding.temperatureValue.text = result.temperature.toString()
//                binding.feelsLikeValue.text = result.feelsLike.toString()
//                binding.cityCoordinates.text = "${result.city.lat}/${result.city.lon}"
            }
        }
    }
}