package com.android1.androidonkotlin.viewmodel.weatherHistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.CommonListWeatherCallback
import com.android1.androidonkotlin.model.RepositoryRoomImpl
import com.android1.androidonkotlin.model.RepositoryWeatherAvailable

import java.io.IOException

class WeatherHistoryListViewModel(private val liveData: MutableLiveData<WeatherHistoryListFragmentAppState> = MutableLiveData<WeatherHistoryListFragmentAppState>()) :
    ViewModel() {

    lateinit var repository: RepositoryWeatherAvailable

    fun getLiveData(): MutableLiveData<WeatherHistoryListFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = RepositoryRoomImpl()
    }

    fun getAllHistory() {
        liveData.value = WeatherHistoryListFragmentAppState.Loading
        Thread{
            repository.getWeatherAll(callback)
        }.start()
    }

    private val callback = object : CommonListWeatherCallback {
        override fun onResponse(listWeather: List<WeatherItem>) {
            liveData.postValue(WeatherHistoryListFragmentAppState.SuccessMulti(listWeather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(WeatherHistoryListFragmentAppState.Error(e))
        }
    }

}