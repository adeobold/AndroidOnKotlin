package com.android1.androidonkotlin.viewmodel

import com.android1.androidonkotlin.domain.WeatherItem

sealed class AppState {
    data class SuccessOne(val weatherData: WeatherItem) : AppState()
    data class SuccessMulti(val weatherList: List<WeatherItem>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}