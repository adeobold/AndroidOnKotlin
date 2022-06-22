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

        findViewById<Button>(R.id.btnHello).setOnClickListener{
            Log.d("$$$", "Нажали на кнопку!")
            val itemMoscow = WeatherItem("Москва", 25.0, 747, 30, 3)
            Log.d("$$$", "Создали объект класса WeatherItem")
            val txtInfo = findViewById<TextView>(R.id.txtInfo)
            txtInfo.text = "Погода в городе ${itemMoscow.town}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
        }

    }
}

data class WeatherItem(val town: String, val temperature: Double,
                       val pressure: Int, val humidity: Int, val wind: Int)

