package com.android1.androidonkotlin.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android1.androidonkotlin.databinding.WeatherFragmentBinding
import com.android1.androidonkotlin.viewmodel.AppState


class WeatherListFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    private var _binding: WeatherFragmentBinding? = null
    private val binding: WeatherFragmentBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.sentRequest()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.txtCity.text = "Произошла ошибка при загрузке"
            }
            AppState.Loading -> {
                binding.txtCity.text = "Идет загрузка..."
            }
            is AppState.Success -> {
                val result = appState.weatherData
                binding.txtCity.text = result.city.name
                binding.txtTemp.text = result.temperature.toString()
                binding.txtHumidity.text = result.humidity.toString()
                binding.txtWind.text = result.wind.toString()
            }
        }
    }


}