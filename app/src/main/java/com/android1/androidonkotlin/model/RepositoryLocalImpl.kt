package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

class RepositoryLocalImpl:Repository {
    override fun getListWeather(): List<WeatherItem> {
        return listOf(WeatherItem())
    }

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        return WeatherItem()
    }
}