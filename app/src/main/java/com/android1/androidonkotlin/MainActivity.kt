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

        val txtInfo = findViewById<TextView>(R.id.txtInfo)

        val itemMoscow = WeatherItem("Москва", 25.0, 747, 30, 3)
        val itemSamara = WeatherItem("Самара", 27.0, 750, 40, 1)
        val itemVolgograd = WeatherItem("Волгоград", 30.0, 734, 50, 5)

        val itemMoscowCopy = itemMoscow.copy()
        Log.d("$$$", "Создали копию объекта класса WeatherItem ${itemMoscowCopy.town}")

        findViewById<Button>(R.id.btnHello).setOnClickListener{
            Log.d("$$$", "Нажали на кнопку!")
            txtInfo.text = "Погода в городе ${itemMoscow.town}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

        findViewById<Button>(R.id.btnSetWeather).setOnClickListener{
            itemMoscow.setWeather("Москва")
            txtInfo.text = "Измененная погода в городе ${itemMoscow.town}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

        findViewById<Button>(R.id.btnFavorites).setOnClickListener{

            FavoritesWeatherItems.addItem(itemMoscow)
            FavoritesWeatherItems.addItem(itemSamara)
            FavoritesWeatherItems.addItem(itemVolgograd)

            Log.d("$$$", "${FavoritesWeatherItems.getItemCount()}")
            FavoritesWeatherItems.getList().forEach { Log.d("$$$", it.town)}

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

object FavoritesWeatherItems{

    private val favorites = mutableListOf<WeatherItem>()

    fun getItemCount(): Int {
        return favorites.size
    }

    fun addItem(item: WeatherItem){
        favorites.add(item)
    }

    fun getList(): MutableList<WeatherItem> {
        return favorites
    }

}