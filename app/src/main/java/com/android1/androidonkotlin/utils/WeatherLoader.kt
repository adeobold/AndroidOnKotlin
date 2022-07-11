package com.android1.androidonkotlin.utils

import android.os.Build
import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader {
    fun request(lat: Double, lon: Double, block: (weather: WeatherDTO) -> Unit) {
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        val myConnection: HttpsURLConnection?
        myConnection = uri.openConnection() as HttpsURLConnection
        try{
            myConnection.readTimeout = 5000
            myConnection.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            Thread {
                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                block(weatherDTO)
            }.start()
        }catch (e:Exception){

        }finally {
            myConnection.disconnect()
        }




    }
}