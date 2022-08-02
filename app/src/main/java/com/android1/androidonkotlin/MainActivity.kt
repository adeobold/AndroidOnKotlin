package com.android1.androidonkotlin

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android1.androidonkotlin.databinding.ActivityMainBinding
import com.android1.androidonkotlin.view.GlobalBroadcastReceiver
import com.android1.androidonkotlin.view.contentprovider.ContentProviderFragment
import com.android1.androidonkotlin.view.maps.MapsFragment
import com.android1.androidonkotlin.view.weatherHistory.WeatherHistoryListFragment
import com.android1.androidonkotlin.view.weatherList.CitiesListFragment


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
                val tag = "WeatherHistoryListFragment"
                if (supportFragmentManager.findFragmentByTag(tag) == null) {
                    navigateTo(WeatherHistoryListFragment(), tag)
                }
                true
            }
            R.id.menu_content_provider -> {
                val tag = "ContentProviderFragment"
                if (supportFragmentManager.findFragmentByTag(tag) == null) {
                    navigateTo(ContentProviderFragment(), tag)
                }
                true
            }
            R.id.menu_google_maps -> {
                val tag = "MapsFragment"
                if (supportFragmentManager.findFragmentByTag("MapsFragment") == null) {
                    navigateTo(MapsFragment(), tag)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun navigateTo(fragment: Fragment, tag: String) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(
                    R.id.container,
                    fragment,
                    tag
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

}