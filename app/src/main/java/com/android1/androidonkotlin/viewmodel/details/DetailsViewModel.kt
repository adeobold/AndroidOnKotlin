package com.android1.androidonkotlin.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.model.*
import com.android1.androidonkotlin.model.retrofit.RepositoryDetailsRetrofitImpl
import java.io.IOException

class DetailsViewModel(private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()) :
    ViewModel() {

    private lateinit var repository: RepositoryDetails

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {

        if(isConnection()){
            repository = when (2) {
                1 -> {
                    RepositoryDetailsOkHttpImpl()
                }
                2 -> {
                    RepositoryDetailsRetrofitImpl()
                }
                3 -> {
                    RepositoryDetailsWeatherLoaderImpl()
                }
                else -> {
                    RepositoryDetailsLocalImpl()
                }
            }
        } else {
//            repositoryLocationToOneWeather = when (1) {
//                1 -> {
//                    RepositoryRoomImpl()
//                }
//                2 -> {
//                    RepositoryLocalImpl()
//                }
//                else -> {
//                    RepositoryLocalImpl()
//                }
//            }
//            repositoryWeatherAddable = when (0) {
//                1 -> {
//                    RepositoryRoomImpl()
//                }
//                else -> {
//                    RepositoryRoomImpl()
//                }
//            }
        }


    }

    fun getWeather(city: City) {
        choiceRepository()
        liveData.value = DetailsFragmentAppState.Loading
        repository.getWeather(city, callback)
    }

    private val callback = object : CommonWeatherCallback{
        override fun onResponse(weather: WeatherItem) {
            liveData.postValue(DetailsFragmentAppState.Success(weather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(DetailsFragmentAppState.Error(e))
        }
    }

    private fun isConnection(): Boolean {
        return true
    }

}