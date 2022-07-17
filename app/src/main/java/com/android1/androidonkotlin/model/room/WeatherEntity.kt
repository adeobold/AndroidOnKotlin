package com.android1.androidonkotlin.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_entity_table")
data class WeatherEntity(
    @PrimaryKey( autoGenerate = true)
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    var temperature: Double,
    var pressure: Double,
    var humidity: Double,
    var wind: Double,
    var feelsLike: Double
)

