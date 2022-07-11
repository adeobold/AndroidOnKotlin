package com.android1.androidonkotlin

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android1.androidonkotlin.databinding.ActivityMainBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.view.GlobalBroadcastReceiver
import com.android1.androidonkotlin.view.weatherList.WeatherListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val receiver = GlobalBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,  WeatherListFragment.newInstance()).commit()
        }

    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
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