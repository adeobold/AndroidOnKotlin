package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.*
import com.android1.androidonkotlin.model.dto.Fact
import com.android1.androidonkotlin.model.dto.WeatherDTO

class RepositoryDetailsLocalImpl : RepositoryDetails {
    override fun getWeather(city: City, commonWeatherCallback: CommonWeatherCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city?.lat == city.lat && it.city.lon == city.lon }
        commonWeatherCallback.onResponse(response.first())
    }

}