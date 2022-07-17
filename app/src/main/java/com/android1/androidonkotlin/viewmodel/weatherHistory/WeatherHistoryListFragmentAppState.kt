package com.android1.androidonkotlin.viewmodel.weatherHistory

import com.android1.androidonkotlin.domain.WeatherItem

sealed class WeatherHistoryListFragmentAppState {
    data class SuccessMulti(val weatherList: List<WeatherItem>) : WeatherHistoryListFragmentAppState()
    data class Error(val error: Throwable) : WeatherHistoryListFragmentAppState()
    object Loading : WeatherHistoryListFragmentAppState()
}
