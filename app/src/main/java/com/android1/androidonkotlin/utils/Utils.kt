package com.android1.androidonkotlin.utils

import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.dto.Fact
import com.android1.androidonkotlin.model.dto.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

class Utils {
}

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

fun bindDTOWithCity(weatherDTO: WeatherDTO, city: City): WeatherItem {
    val fact: Fact = weatherDTO.fact!!
    return (WeatherItem(city, fact.temp, fact.pressureMm, fact.humidity, fact.windSpeed, fact.feelsLike))
}