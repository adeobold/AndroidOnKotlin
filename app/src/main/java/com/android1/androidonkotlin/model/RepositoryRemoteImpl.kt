package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getRussianCities
import com.android1.androidonkotlin.domain.getWorldCities

class RepositoryRemoteImpl : Repository {

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        Thread.sleep(2000L)
        return WeatherItem()
    }

    override fun getListWeather(location: Location): List<WeatherItem> {

        var result = mutableListOf<WeatherItem>()

        val cities = when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
        for (i in 0..cities.size) {
            result.add(getWeather(cities[i].city!!.lat, cities[i].city!!.lon))
        }
        return result
    }

}