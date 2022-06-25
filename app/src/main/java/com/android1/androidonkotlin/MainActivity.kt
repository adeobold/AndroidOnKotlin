package com.android1.androidonkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android1.androidonkotlin.databinding.ActivityMainBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.view.WeatherFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,  WeatherFragment.newInstance()).commit()
        }



//        val txtInfo = findViewById<TextView>(R.id.txtInfo)
//
//        val itemMoscow = WeatherItem()
//
//        val itemMoscowCopy = itemMoscow.copy()
//        Log.d("$$$", "Создали копию объекта класса WeatherItem ${itemMoscowCopy.city.name}")
//
//        findViewById<Button>(R.id.btnHello).setOnClickListener{
//            Log.d("$$$", "Нажали на кнопку!")
//            txtInfo.text = "Погода в городе ${itemMoscow.city.name}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
//        }
//
//        findViewById<Button>(R.id.btnSetWeather).setOnClickListener{
//            txtInfo.text = "Измененная погода в городе ${itemMoscow.city.name}: ${itemMoscow.temperature} градусов, давление ${itemMoscow.pressure}"
//        }
//
//        findViewById<Button>(R.id.btnFavorites).setOnClickListener{
//
//            FavoritesWeatherItems.addItem(itemMoscow)
//
//            Log.d("$$$", "${FavoritesWeatherItems.getItemCount()}")
//            FavoritesWeatherItems.getList().forEach { Log.d("$$$", it.city.name)}
//
//        }



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