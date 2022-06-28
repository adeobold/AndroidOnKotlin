package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

class RepositoryLocalImpl : RepositoryOne, RepositoryMany {

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        return WeatherItem()
    }

    override fun getListWeather(location: Location): List<WeatherItem> {
        return when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }
}