package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getRussianCities
import com.android1.androidonkotlin.domain.getWorldCities

class RepositoryRemoteImpl : Repository {

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        return WeatherItem() // здесь будет функция загрузки погоды из яндекса
    }

    override fun getListWeather(location: Location): List<WeatherItem> {

        val result = mutableListOf<WeatherItem>()

        val cities = when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
        for (i in cities.indices) {
            result.add(getWeather(cities[i].city!!.lat, cities[i].city!!.lon))
        }
        return result
    }

}