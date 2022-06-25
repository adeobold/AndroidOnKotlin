package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

interface Repository {
    fun getListWeather():List<WeatherItem>
    fun getWeather( lat: Double, lon: Double):WeatherItem
}