package com.android1.androidonkotlin.model.retrofit

import com.android1.androidonkotlin.model.dto.WeatherDTO
import com.android1.androidonkotlin.utils.YANDEX_API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/v2/informers")
    fun getWeather(
        @Header(YANDEX_API_KEY) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

}