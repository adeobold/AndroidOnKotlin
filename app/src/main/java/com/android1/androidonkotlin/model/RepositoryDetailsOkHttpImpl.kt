package com.android1.androidonkotlin.model

import android.util.Log
import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getDefaultCity
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.YANDEX_API_KEY
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryDetailsOkHttpImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callbackForAll: CallbackForAll) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)

        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                callbackForAll.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful&&response.body!=null) {
                    response.body?.let {
                        val responseString = it.string()
                        val weatherDTO = Gson().fromJson(responseString, WeatherDTO::class.java)
                        callbackForAll.onResponse(weatherDTO)
                    }
                } else {
                    callbackForAll.onFailure(IOException("Ответ сервера пришел с ошибкой"))
                }
            }

        })
    }
}