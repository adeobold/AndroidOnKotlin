package com.android1.androidonkotlin.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android1.androidonkotlin.databinding.FragmentDetailsBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.BUNDLE_CITY_KEY
import com.android1.androidonkotlin.utils.BUNDLE_WEATHER_DTO_KEY
import com.android1.androidonkotlin.utils.WAVE_KEY
import com.android1.androidonkotlin.utils.WeatherLoader

class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    lateinit var weatherLocal: WeatherItem

    val reciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)?.let { weatherDTO ->
                    bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(reciever)
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

//                WeatherLoader.request(cityLocal.lat, cityLocal.lon) { weatherDTO ->
//                    bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
//                }

                this.weatherLocal = weatherLocal

                LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                    reciever,
                    IntentFilter(WAVE_KEY)
                )

                requireActivity().startService(Intent(requireContext(), DetailsServiceIntent::class.java).apply {
                    putExtra(BUNDLE_CITY_KEY, cityLocal)
                })

            }
        }
    }

    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: WeatherItem,
        weatherDTO: WeatherDTO
    ) {
        //requireActivity().runOnUiThread {
            renderData(weatherLocal.apply {
                weatherDTO.fact?.let { fact ->
                    weatherLocal.temperature = fact.temp
                    weatherLocal.humidity = fact.humidity
                    weatherLocal.pressure = fact.pressureMm
                    weatherLocal.wind = fact.windSpeed
                    weatherLocal.feelsLike = fact.feelsLike
                }

            })
        //}
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(weather: WeatherItem) {
        with(binding) {
            cityName.text = weather.city?.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
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