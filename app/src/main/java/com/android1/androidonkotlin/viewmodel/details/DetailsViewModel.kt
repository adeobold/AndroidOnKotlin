package com.android1.androidonkotlin.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.*
import com.android1.androidonkotlin.model.retrofit.RepositoryRetrofitImpl
import java.io.IOException

class DetailsViewModel(private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()) :
    ViewModel() {

    private lateinit var repositoryLoadable: RepositoryWeatherByCityLoadable
    lateinit var repositoryWeatherSavable: RepositoryWeatherSavable

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        return liveData
    }

    private fun choiceRepository() {

        if(isConnection()){
            repositoryLoadable = when (2) {
                1 -> {
                    RepositoryOkHttpImpl()
                }
                2 -> {
                    RepositoryRetrofitImpl()
                }
                3 -> {
                    RepositoryWeatherLoaderImpl()
                }
                else -> {
                    RepositoryLocalImpl()
                }
            }
            repositoryWeatherSavable = RepositoryRoomImpl()
        } else {
            repositoryLoadable = when (1) {
                1 -> {
                    RepositoryRoomImpl()
                }
                2 -> {
                    RepositoryLocalImpl()
                }
                else -> {
                    RepositoryLocalImpl()
                }
            }
            repositoryWeatherSavable = RepositoryRoomImpl()
        }


    }

    fun getWeather(city: City) {
        choiceRepository()
        liveData.value = DetailsFragmentAppState.Loading
        repositoryLoadable.getWeather(city, callback)
    }

    private val callback = object : CommonWeatherCallback{
        override fun onResponse(weather: WeatherItem) {
            if (isConnection())
                repositoryWeatherSavable.addWeather(weather)
            liveData.postValue(DetailsFragmentAppState.Success(weather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(DetailsFragmentAppState.Error(e))
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

}