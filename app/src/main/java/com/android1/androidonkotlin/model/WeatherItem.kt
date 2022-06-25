package com.android1.androidonkotlin.model

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.*

data class WeatherItem(val city: City = getDefaultCity(), val date: Calendar = Calendar.getInstance(), val temperature: Double = 21.0,
                       val pressure: Int = 747, val humidity: Int = 20, val wind: Int = 5)

data class City(val name: String, val lat: Double, val lon: Double)

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)


