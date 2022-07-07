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
import com.android1.androidonkotlin.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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

        weatherItem?.let {

            val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${it.city?.lat}&lon=${it.city?.lon}")
            val connection: HttpsURLConnection?
            connection = uri.openConnection() as HttpsURLConnection
            connection.connectTimeout = 5000
            connection.addRequestProperty("X-Yandex-API-Key","..")

            Thread{
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader),WeatherDTO::class.java)

                requireActivity().runOnUiThread{
                    renderData(it.apply {
                        temperature = weatherDTO.fact.temp
                        humidity = weatherDTO.fact.humidity
                        pressure = weatherDTO.fact.pressureMm
                        wind = weatherDTO.fact.windSpeed
                    })
                }

            }.start()



        }








//        arguments?.let {
//            arg -> arg.getParcelable<WeatherItem>(BUNDLE_WEATHER_EXTRA)?.let {
//                weather -> renderData(weather)
//            }
//        }
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