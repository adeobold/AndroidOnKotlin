package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.dto.WeatherDTO
import java.io.IOException

fun interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double, callbackForAll: CallbackForAll)
}

fun interface RepositoryOne {
    fun getWeather(lat: Double, lon: Double, callbackForAll: CallbackForAll)
}

fun interface RepositoryCitiesList {
    fun getListCities(location: Location): List<WeatherItem>
}

interface CallbackForAll{
    fun onResponse(weatherDTO: WeatherDTO)
    fun onFailure(e: IOException)
}