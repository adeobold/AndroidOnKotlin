package com.android1.androidonkotlin

import android.app.Application
import androidx.room.Room
import com.android1.androidonkotlin.model.room.WeatherDatabase
import com.android1.androidonkotlin.utils.ROOM_DB_NAME_WEATHER

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        WeatherApp = this
    }

    companion object {
        private var WeatherApp: WeatherApp? = null
        private var weatherDatabase: WeatherDatabase? = null
        fun getMyApp() = WeatherApp!!
        fun getWeatherDatabase(): WeatherDatabase {
            if (weatherDatabase == null) {
                weatherDatabase = Room.databaseBuilder(
                    getMyApp(),
                    WeatherDatabase::class.java,
                    ROOM_DB_NAME_WEATHER
                ).allowMainThreadQueries() // TODO HW убрать allowMainThreadQueries
                    .build()
            }
            return weatherDatabase!!
        }
    }
}
