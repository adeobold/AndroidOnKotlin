package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.YANDEX_API_KEY
import com.android1.androidonkotlin.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryDetailsWeatherLoaderImpl : RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callbackForAll: CallbackForAll) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        val myConnection: HttpsURLConnection?
        myConnection = uri.openConnection() as HttpsURLConnection
        try {
            myConnection.readTimeout = 5000
            myConnection.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            Thread {
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callbackForAll.onResponse(weatherDTO)
            }.start()
        } catch (e: IOException) {
            callbackForAll.onFailure(e)
        } finally {
            myConnection.disconnect()
        }
    }

}