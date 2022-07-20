package com.android1.androidonkotlin.model.room

import android.database.Cursor
import androidx.room.*

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(weatherEntity: WeatherEntity)

    @Update
    fun updateRoom(weatherEntity:WeatherEntity)

    @Query("SELECT * FROM weather_entity_table WHERE lat=:mLat AND lon=:mLon")
    fun getWeatherByLocation(mLat: Double, mLon: Double): List<WeatherEntity>

    @Query("SELECT * FROM weather_entity_table")
    fun getWeatherAll(): List<WeatherEntity>

    @Query("DELETE FROM weather_entity_table WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id, name, temperature FROM weather_entity_table")
    fun getWeatherCursor(): Cursor

    @Query("SELECT id, name, temperature FROM weather_entity_table WHERE id = :id ") //ORDER BY id DESC LIMIT 1
    fun getWeatherCursor(id: Long): Cursor

}