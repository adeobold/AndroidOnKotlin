package com.android1.androidonkotlin.viewmodel.citieslist

import com.android1.androidonkotlin.domain.WeatherItem

sealed class CitiesListFragmentAppState {
    data class SuccessOne(val weatherData: WeatherItem) : CitiesListFragmentAppState()
    data class SuccessMulti(val weatherList: List<WeatherItem>) : CitiesListFragmentAppState()
    data class Error(val error: Throwable) : CitiesListFragmentAppState()
    object Loading : CitiesListFragmentAppState()
}