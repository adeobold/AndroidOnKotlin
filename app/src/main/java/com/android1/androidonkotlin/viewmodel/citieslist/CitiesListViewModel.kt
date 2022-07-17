package com.android1.androidonkotlin.viewmodel.citieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android1.androidonkotlin.model.*
import com.android1.androidonkotlin.viewmodel.citieslist.CitiesListFragmentAppState.Error
import kotlin.random.Random

class CitiesListViewModel(private val liveData: MutableLiveData<CitiesListFragmentAppState> = MutableLiveData<CitiesListFragmentAppState>()) : ViewModel() {

    private lateinit var repositoryCitiesList: RepositoryCitiesList

    fun getLiveData(): MutableLiveData<CitiesListFragmentAppState> {
        return liveData
    }

    private fun choiceRepository() {
        repositoryCitiesList = RepositoryCitiesListImpl()
    }

    fun getWeatherListForRussia(){
        sentRequest(Location.Russian)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location) {

        choiceRepository()

        liveData.value = CitiesListFragmentAppState.Loading

        val rand = Random(System.nanoTime())

        Thread {
            if ((0..3).random(rand) == 2) {
                liveData.postValue(
                    Error(IllegalStateException("что-то пошло не так"))
                )
            } else {
                liveData.postValue(CitiesListFragmentAppState.SuccessMulti(repositoryCitiesList.getListCities(location)))
            }
        }.start()

    }

}