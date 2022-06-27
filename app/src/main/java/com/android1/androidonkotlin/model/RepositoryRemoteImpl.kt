package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

class RepositoryRemoteImpl:Repository {
    override fun getListWeather(): List<WeatherItem> {
        Thread.sleep(4000L)
        return listOf(WeatherItem())
    }

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        Thread.sleep(2000L)
        return WeatherItem()
    }
}