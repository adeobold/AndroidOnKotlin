package com.android1.androidonkotlin

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.android1.androidonkotlin.databinding.ActivityMainBinding
import com.android1.androidonkotlin.utils.SP_DB_NAME_IS_RUSSIAN
import com.android1.androidonkotlin.utils.SP_KEY_IS_RUSSIAN
import com.android1.androidonkotlin.view.GlobalBroadcastReceiver
import com.android1.androidonkotlin.view.contentprovider.ContentProviderFragment
import com.android1.androidonkotlin.view.maps.MapsFragment
import com.android1.androidonkotlin.view.weatherList.CitiesListFragment
import com.android1.androidonkotlin.view.weatherHistory.WeatherHistoryListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val receiver = GlobalBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                val fragmentHistory =
                    supportFragmentManager.findFragmentByTag("WeatherHistoryListFragment")
                if (fragmentHistory == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                WeatherHistoryListFragment(),
                                "WeatherHistoryListFragment"
                            )
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            R.id.menu_content_provider -> {
                val fragmentContentProvider =
                    supportFragmentManager.findFragmentByTag("ContentProviderFragment")
                if (fragmentContentProvider == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(
                                R.id.container,
                                ContentProviderFragment(),
                                "ContentProviderFragment"
                            )
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            R.id.menu_google_maps -> {
                val fragmentMaps = supportFragmentManager.findFragmentByTag("MapsFragment")
                if (fragmentMaps == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container, MapsFragment(), "MapsFragment")
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}