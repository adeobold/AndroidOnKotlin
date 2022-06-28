package com.android1.androidonkotlin.domain

import java.util.*

data class WeatherItem(val city: City = getDefaultCity(), val date: Calendar = Calendar.getInstance(), val temperature: Double = 21.0,
                       val pressure: Int = 747, val humidity: Int = 20, val wind: Int = 5)

data class City(val name: String, val lat: Double, val lon: Double)

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)

fun getWorldCities(): List<WeatherItem> {
    return listOf(
        WeatherItem(City("Лондон", 51.5085300, -0.1257400), Calendar.getInstance(), 1.0, 2),
        WeatherItem(City("Токио", 35.6895000, 139.6917100), Calendar.getInstance(),3.0, 4),
        WeatherItem(City("Париж", 48.8534100, 2.3488000), Calendar.getInstance(),5.0, 6),
        WeatherItem(City("Берлин", 52.52000659999999, 13.404953999999975), Calendar.getInstance(),7.0, 8),
        WeatherItem(City("Рим", 41.9027835, 12.496365500000024), Calendar.getInstance(),9.0, 10),
        WeatherItem(City("Минск", 53.90453979999999, 27.561524400000053),Calendar.getInstance(), 11.0, 12),
        WeatherItem(City("Стамбул", 41.0082376, 28.97835889999999), Calendar.getInstance(),13.0, 14),
        WeatherItem(City("Вашингтон", 38.9071923, -77.03687070000001), Calendar.getInstance(),15.0, 16),
        WeatherItem(City("Киев", 50.4501, 30.523400000000038), Calendar.getInstance(),17.0, 18),
        WeatherItem(City("Пекин", 39.90419989999999, 116.40739630000007), Calendar.getInstance(),19.0, 20)
    )
}

fun getRussianCities(): List<WeatherItem> {
    return listOf(
        WeatherItem(City("Москва", 55.755826, 37.617299900000035), Calendar.getInstance(),1.0, 2),
        WeatherItem(City("Санкт-Петербург", 59.9342802, 30.335098600000038), Calendar.getInstance(),3.0, 3),
        WeatherItem(City("Новосибирск", 55.00835259999999, 82.93573270000002), Calendar.getInstance(),5.0, 6),
        WeatherItem(City("Екатеринбург", 56.83892609999999, 60.60570250000001), Calendar.getInstance(),7.0, 8),
        WeatherItem(City("Нижний Новгород", 56.2965039, 43.936059), Calendar.getInstance(),9.0, 10),
        WeatherItem(City("Казань", 55.8304307, 49.06608060000008),Calendar.getInstance(), 11.0, 12),
        WeatherItem(City("Челябинск", 55.1644419, 61.4368432), Calendar.getInstance(),13.0, 14),
        WeatherItem(City("Омск", 54.9884804, 73.32423610000001), Calendar.getInstance(),15.0, 16),
        WeatherItem(City("Ростов-на-Дону", 47.2357137, 39.701505), Calendar.getInstance(),17.0, 18),
        WeatherItem(City("Уфа", 54.7387621, 55.972055400000045), Calendar.getInstance(),19.0, 20)
    )
}


