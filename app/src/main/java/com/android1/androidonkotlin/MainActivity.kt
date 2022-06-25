package com.android1.androidonkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android1.androidonkotlin.model.WeatherItem

class MainActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtInfo = findViewById<TextView>(R.id.txtInfo)

        val itemMoscow = WeatherItem()

        val itemMoscowCopy = itemMoscow.copy()
        Log.d("$$$", "Создали копию объекта класса WeatherItem ${itemMoscowCopy.city.name}")

        findViewById<Button>(R.id.btnHello).setOnClickListener{
            Log.d("$$$", "Нажали на кнопку!")
            txtInfo.text = "Погода в городе ${itemMoscow.city.name}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

        findViewById<Button>(R.id.btnSetWeather).setOnClickListener{
            txtInfo.text = "Измененная погода в городе ${itemMoscow.city.name}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

        findViewById<Button>(R.id.btnFavorites).setOnClickListener{

            FavoritesWeatherItems.addItem(itemMoscow)

            Log.d("$$$", "${FavoritesWeatherItems.getItemCount()}")
            FavoritesWeatherItems.getList().forEach { Log.d("$$$", it.city.name)}

        }



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