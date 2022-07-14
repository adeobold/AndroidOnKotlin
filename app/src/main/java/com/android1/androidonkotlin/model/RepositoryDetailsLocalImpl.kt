package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.domain.getDefaultCity
import com.android1.androidonkotlin.domain.getRussianCities
import com.android1.androidonkotlin.domain.getWorldCities
import com.android1.androidonkotlin.model.dto.Fact
import com.android1.androidonkotlin.model.dto.WeatherDTO

class RepositoryDetailsLocalImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callbackForAll: CallbackForAll) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city?.lat==lat&&it.city.lon==lon  }
        callbackForAll.onResponse(convertModelToDto(response.first()))
    }

    private  fun convertDtoToModel(weatherDTO: WeatherDTO): WeatherItem {
        val fact: Fact? = weatherDTO.fact
        return (WeatherItem(getDefaultCity(), fact!!.temp, fact.feelsLike))
    }

    private fun convertModelToDto(weather:WeatherItem): WeatherDTO{
        val fact = Fact(weather.feelsLike, weather.pressure, weather.temperature, weather.wind, weather.humidity)
        return WeatherDTO(fact)
    }
}