package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

class RepositoryRemoteImpl:Repository {
    override fun getListWeather(): List<WeatherItem> {
        Thread{
            Thread.sleep(200L)
        }.start()
        return listOf(WeatherItem())
    }

    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        Thread{
            Thread.sleep(300L)
        }.start()
        return WeatherItem()
    }
}