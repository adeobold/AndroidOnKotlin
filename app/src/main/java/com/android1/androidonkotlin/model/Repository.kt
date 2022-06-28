package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

interface Repository {
    fun getWeather( lat: Double, lon: Double):WeatherItem
    fun getListWeather(location:Location):List<WeatherItem>
}
