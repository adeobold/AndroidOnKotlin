package com.android1.androidonkotlin.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_entity_table")
data class WeatherEntity(
    @PrimaryKey( autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val lat: Double = 1.0,
    val lon: Double = 1.0,
    var temperature: Double = 1.0,
    var pressure: Double = 1.0,
    var humidity: Double = 1.0,
    var wind: Double = 1.0,
    var feelsLike: Double = 1.0
)

