package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getRussianCities
import com.android1.androidonkotlin.domain.getWorldCities

class RepositoryLocalImpl : Repository {

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