package com.android1.androidonkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemMoscow = WeatherItem("Москва", 25.0, 747, 30, 3)

        findViewById<Button>(R.id.btnHello).setOnClickListener{
            Log.d("$$$", "Нажали на кнопку!")
            Log.d("$$$", "Создали объект класса WeatherItem")
            val txtInfo = findViewById<TextView>(R.id.txtInfo)
            txtInfo.text = "Погода в городе ${itemMoscow.town}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

        findViewById<Button>(R.id.btnSetWeather).setOnClickListener{
            val txtInfo = findViewById<TextView>(R.id.txtInfo)
            itemMoscow.setWeather("Москва")
            txtInfo.text = "Измененная погода в городе ${itemMoscow.town}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

    }
}

data class WeatherItem(var town: String, var temperature: Double,
                       var pressure: Int, var humidity: Int, var wind: Int){

    fun setWeather(town: String, temperature: Double = 21.0,
                   pressure: Int = 747, humidity: Int = 30, wind: Int = 2) {
        this.town = town
        this.temperature = temperature
        this.pressure = pressure
        this.humidity = humidity
        this.wind = wind
    }

}

//object FavoritesWeatherItems{
//
//    private lateinit var Favorites: List<WeatherItem>
//
//    init {
//        Favorites = List(1)
//    }
//
//    fun getFavorites(): List<WeatherItem> {
//        return Favorites
//    }
//
//    fun getItemCount() = Favorites.size
//
//}