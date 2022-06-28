package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem

fun interface RepositoryOne {
    fun getWeather( lat: Double, lon: Double):WeatherItem
}
fun interface RepositoryMany {
    fun getListWeather(location:Location):List<WeatherItem>
}

sealed class Location{
    object Russian:Location()
    object World:Location()
}
