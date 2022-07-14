package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

interface Repository {
    fun getWeather(lat: Double, lon: Double): WeatherItem
    fun getListWeather(location: Location): List<WeatherItem>
}

fun interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double): WeatherItem
}

fun interface RepositoryOne {
    fun getWeather(lat: Double, lon: Double): WeatherItem
}

fun interface RepositoryCitiesList {
    fun getListCities(location: Location): List<WeatherItem>
}