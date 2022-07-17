package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.dto.WeatherDTO
import java.io.IOException

fun interface RepositoryDetails {
    fun getWeather(city: City, commonWeatherCallback: CommonWeatherCallback)
}

fun interface RepositoryWeatherByCity {
    fun getWeather(city: City, callback: CommonWeatherCallback)
}

fun interface RepositoryWeatherAvailable {
    fun getWeatherAll(callback: CommonListWeatherCallback)
}

fun interface RepositoryWeatherSave {
    fun addWeather(weather: WeatherItem)
}

fun interface RepositoryCitiesList {
    fun getListCities(location: Location): List<WeatherItem>
}

interface CommonWeatherCallback{
    fun onResponse(weather: WeatherItem)
    fun onFailure(e: IOException)
}

interface CommonListWeatherCallback{
    fun onResponse(weather: List<WeatherItem>)
    fun onFailure(e: IOException)
}