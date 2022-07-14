package com.android1.androidonkotlin.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.databinding.FragmentDetailsBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    //lateinit var weatherLocal: WeatherItem

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
                //this.weatherLocal = weatherLocal

//                WeatherLoader.request(cityLocal.lat, cityLocal.lon) { weatherDTO ->
//                    bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
//                }

                val client = OkHttpClient()
                val builder = Request.Builder()
                builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                builder.url("https://api.weather.yandex.ru/v2/informers?lat=${cityLocal.lat}&lon=${cityLocal.lon}")
                val request: Request = builder.build()
                val call: Call = client.newCall(request)

                call.enqueue(object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("@@@", "Проблема до отправки на сервер")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful&&response.body!=null) {
                            response.body?.let {
                                val responseString = it.string()
                                Log.d("@@@", responseString)
                                val weatherDTO = Gson().fromJson(responseString, WeatherDTO::class.java)
                                weatherLocal.temperature = weatherDTO.fact!!.temp
                                weatherLocal.humidity = weatherDTO.fact.humidity
                                weatherLocal.pressure = weatherDTO.fact.pressureMm
                                weatherLocal.wind = weatherDTO.fact.windSpeed
                                weatherLocal.feelsLike = weatherDTO.fact.feelsLike
                            }
                            requireActivity().runOnUiThread{
                                renderData(weatherLocal)
                            }
                        } else {
                            Log.d("@@@", "Проблема с ответом со стороны сервера")
                        }
                    }

                })

            }
        }
    }

    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: WeatherItem,
        weatherDTO: WeatherDTO
    ) {
        renderData(weatherLocal.apply {
            weatherDTO.fact?.let { fact ->
                weatherLocal.temperature = fact.temp
                weatherLocal.humidity = fact.humidity
                weatherLocal.pressure = fact.pressureMm
                weatherLocal.wind = fact.windSpeed
                weatherLocal.feelsLike = fact.feelsLike
            }

        })
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}