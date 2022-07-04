package com.android1.androidonkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android1.androidonkotlin.databinding.ActivityMainBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.view.weatherList.WeatherListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,  WeatherListFragment.newInstance()).commit()
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