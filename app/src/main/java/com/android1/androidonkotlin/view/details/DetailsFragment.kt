package com.android1.androidonkotlin.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android1.androidonkotlin.databinding.FragmentDetailsBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.viewmodel.details.DetailsFragmentAppState
import com.android1.androidonkotlin.viewmodel.details.DetailsViewModel

class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    private lateinit var weatherLocal: WeatherItem

    private val viewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
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
            this.weatherLocal = weatherLocal
            weatherLocal.city?.let { cityLocal ->
                viewModel.getWeather(cityLocal.lat, cityLocal.lon)
                viewModel.getLiveData().observe(viewLifecycleOwner) {
                    renderData(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(detailsFragmentAppState: DetailsFragmentAppState) {
        when (detailsFragmentAppState) {
            is DetailsFragmentAppState.Error -> {}
            is DetailsFragmentAppState.Loading -> {}
            is DetailsFragmentAppState.Success -> {
                with(binding) {
                    val weatherDTO = detailsFragmentAppState.weatherData
                    cityName.text = weatherLocal.city?.name
                    temperatureValue.text = weatherDTO.fact?.temp.toString()
                    feelsLikeValue.text = weatherDTO.fact?.feelsLike.toString()
                    cityCoordinates.text = "${weatherLocal.city?.lat}/${weatherLocal.city?.lon}"
                }
            }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}