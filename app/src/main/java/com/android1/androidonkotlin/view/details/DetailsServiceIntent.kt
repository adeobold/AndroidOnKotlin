package com.android1.androidonkotlin.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsServiceIntent: IntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let { it ->
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?.let {
                val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${it.lat}&lon=${it.lon}")
                val myConnection: HttpsURLConnection?
                myConnection = uri.openConnection() as HttpsURLConnection
                try {
                    myConnection.readTimeout = 3000
                    myConnection.addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                    Thread {
                        val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                        val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                            putExtra(BUNDLE_WEATHER_DTO_KEY,weatherDTO)
                            action = WAVE_KEY
                        })
                    }.start()
                } catch (e: Exception) {

                } finally {
                    myConnection.disconnect()
                }
            }
        }
    }

}