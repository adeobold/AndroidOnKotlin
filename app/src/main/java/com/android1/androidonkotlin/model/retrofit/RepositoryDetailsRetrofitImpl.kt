package com.android1.androidonkotlin.model.retrofit

import com.android1.androidonkotlin.BuildConfig
import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.model.CommonWeatherCallback
import com.android1.androidonkotlin.model.RepositoryDetails
import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.bindDTOWithCity
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RepositoryDetailsRetrofitImpl: RepositoryDetails {
    override fun getWeather(city: City, commonWeatherCallback: CommonWeatherCallback) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://api.weather.yandex.ru")
        retrofitImpl.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofitImpl.build().create(WeatherAPI::class.java)
        api.getWeather(BuildConfig.WEATHER_API_KEY,city.lat,city.lon).enqueue(object : Callback<WeatherDTO> {
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                if(response.isSuccessful&&response.body()!=null){
                    commonWeatherCallback.onResponse(bindDTOWithCity(response.body()!!,city))
                }else {
                    commonWeatherCallback.onFailure(IOException("403 404"))
                }

            }
            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                val ioException = IOException(t.message) // велосипед
                commonWeatherCallback.onFailure(ioException)
            }
        })
    }
}