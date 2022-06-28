package com.android1.androidonkotlin.view.details
import com.android1.androidonkotlin.domain.WeatherItem

fun interface OnItemClick {
    fun onItemClick(weather: WeatherItem)
}