package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getDefaultCity

class RepositoryDetailsLocalImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double): WeatherItem {
        return WeatherItem(getDefaultCity())
    }
}