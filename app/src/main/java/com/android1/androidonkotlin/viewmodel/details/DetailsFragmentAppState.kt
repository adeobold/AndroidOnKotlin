package com.android1.androidonkotlin.viewmodel.details

import com.android1.androidonkotlin.domain.WeatherItem

sealed class DetailsFragmentAppState {
    data class Success(val weatherData: WeatherItem) : DetailsFragmentAppState()
    data class Error(val error: Throwable) : DetailsFragmentAppState()
    object Loading : DetailsFragmentAppState()
}