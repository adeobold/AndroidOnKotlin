package com.android1.androidonkotlin.utils

import android.os.Build
import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object WeatherLoader {
    fun request(lat: Double, lon: Double, block: (weather: WeatherDTO) -> Unit) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        val myConnection: HttpURLConnection?

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
        Thread {
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            block(weatherDTO)
        }.start()
    }
}