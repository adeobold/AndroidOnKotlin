package com.android1.androidonkotlin.model

import com.android1.androidonkotlin.WeatherApp
import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.room.WeatherEntity

class RepositoryRoomImpl : RepositoryWeatherByCityLoadable, RepositoryWeatherSavable,
    RepositoryWeatherAvailable {

    override fun getWeather(city: City, commonWeatherCallback: CommonWeatherCallback) {

        var roomWeatherList =
            WeatherApp.getWeatherDatabase().weatherDao().getWeatherByLocation(city.lat, city.lon)
        if (roomWeatherList.isEmpty()) {
                        roomWeatherList = List(1) {
                WeatherEntity(
                    999,
                    "Нет загруженных данных",
                    city.lat,
                    city.lon,
                    -999.0,
                    -999.0,
                    -999.0,
                    -999.0,
                    -999.0
                )
            }
        }

        commonWeatherCallback.onResponse(convertHistoryEntityToWeather(roomWeatherList).last())

//        commonWeatherCallback.onResponse(
//            WeatherApp.getWeatherDatabase().weatherDao().getWeatherByLocation(city.lat, city.lon)
//                .let {
//                    convertHistoryEntityToWeather(it).last()
//                })
    }

    override fun addWeather(weather: WeatherItem) {
        convertWeatherToEntity(weather)?.let { weatherEntity ->
            WeatherApp.getWeatherDatabase().weatherDao().insertRoom(weatherEntity)
        }
    }

    override fun getWeatherAll(commonListWeatherCallback: CommonListWeatherCallback) {
        commonListWeatherCallback.onResponse(
            convertHistoryEntityToWeather(
                WeatherApp.getWeatherDatabase().weatherDao().getWeatherAll()
            )
        )
    }

    private fun convertHistoryEntityToWeather(entityList: List<WeatherEntity>): List<WeatherItem> {
        return entityList.map {
            WeatherItem(
                City(it.name, it.lat, it.lon),
                it.temperature,
                it.pressure,
                it.humidity,
                it.wind,
                it.feelsLike
            )
        }
    }

    private fun convertWeatherToEntity(weather: WeatherItem): WeatherEntity? {
        return weather.city?.name?.let {
            WeatherEntity(
                0,
                it,
                weather.city.lat,
                weather.city.lon,
                weather.temperature,
                weather.pressure,
                weather.humidity,
                weather.wind,
                weather.feelsLike
            )
        }
    }


}

