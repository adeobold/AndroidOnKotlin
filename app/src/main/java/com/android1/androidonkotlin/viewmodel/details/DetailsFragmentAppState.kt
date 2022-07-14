package com.android1.androidonkotlin.viewmodel.details

import com.android1.androidonkotlin.model.dto.WeatherDTO

sealed class DetailsFragmentAppState {
    data class Success(val weatherData: WeatherDTO) : DetailsFragmentAppState()
    data class Error(val error: Throwable) : DetailsFragmentAppState()
    object Loading : DetailsFragmentAppState()
}