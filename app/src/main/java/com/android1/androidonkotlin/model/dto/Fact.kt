package com.android1.androidonkotlin.model.dto


import com.google.gson.annotations.SerializedName

data class Fact(
    @SerializedName("condition")
    val condition: String,
    @SerializedName("daytime")
    val daytime: String,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("humidity")
    val humidity: Double,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("obs_time")
    val obsTime: Double,
    @SerializedName("polar")
    val polar: Boolean,
    @SerializedName("pressure_mm")
    val pressureMm: Double,
    @SerializedName("pressure_pa")
    val pressurePa: Double,
    @SerializedName("season")
    val season: String,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)