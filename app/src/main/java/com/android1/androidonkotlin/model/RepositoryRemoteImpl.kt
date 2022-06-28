package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getRussianCities
import com.android1.androidonkotlin.domain.getWorldCities

class RepositoryRemoteImpl:RepositoryOne, RepositoryMany {

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        Thread.sleep(2000L)
        return WeatherItem()
    }

    override fun getListWeather(location: Location): List<WeatherItem> {
        val result: MutableList<WeatherItem> = when (location) {
            Location.Russian -> ({
                getRussianCities()
            }) as MutableList<WeatherItem>
            Location.World -> ({
                getWorldCities()
            }) as MutableList<WeatherItem>
        }
        for (i in 0..result.size){
            result[i] = getWeather(result[i].city.lat, result[i].city.lon)
        }
        return result
    }

}