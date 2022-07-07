package com.android1.androidonkotlin.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android1.androidonkotlin.databinding.FragmentDetailsBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.WeatherLoader

class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherItem =
            arguments?.getParcelable<WeatherItem>(BUNDLE_WEATHER_EXTRA)

        weatherItem?.let { weatherLocal ->
            weatherLocal.city?.let { cityLocal ->
                WeatherLoader.request(cityLocal.lat, cityLocal.lon) { weatherDTO ->
                    bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
                }
            }
        }

    }

    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: WeatherItem,
        weatherDTO: WeatherDTO
    ) {
        requireActivity().runOnUiThread {
            renderData(weatherLocal.apply {
                weatherLocal.temperature = weatherDTO.fact.temp
                weatherLocal.humidity = weatherDTO.fact.humidity
                weatherLocal.pressure = weatherDTO.fact.pressureMm
                weatherLocal.wind = weatherDTO.fact.windSpeed
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: WeatherItem) {
        with(binding) {
            cityName.text = weather.city?.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.pressure.toString()
            cityCoordinates.text = "${weather.city?.lat}/${weather.city?.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "parcelWeather"
        fun newInstance(weather: WeatherItem) = DetailsFragment().also { fr ->
            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }
        }
    }


}