package com.android1.androidonkotlin.view.weatherList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android1.androidonkotlin.model.Location
import com.android1.androidonkotlin.model.Repository
import com.android1.androidonkotlin.model.RepositoryLocalImpl
import com.android1.androidonkotlin.model.RepositoryRemoteImpl
import com.android1.androidonkotlin.viewmodel.AppState
import com.android1.androidonkotlin.viewmodel.AppState.Error
import kotlin.random.Random

class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) : ViewModel() {

    lateinit var repository: Repository

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
    }

    fun getWeatherListForRussia(){
        sentRequest(Location.Russian)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    fun sentRequest(location: Location) {

        liveData.value = AppState.Loading

        val rand = Random(System.nanoTime())

        Thread {
            if ((0..6).random(rand) == 2) {
                liveData.postValue(
                    Error(IllegalStateException("что-то пошло не так"))
                )
            } else {
                liveData.postValue(AppState.SuccessMulti(repository.getListWeather(location)))
            }
        }.start()

    }

    private fun isConnection(): Boolean {
        return true
    }

    override fun onCleared() { // TODO HW *** Пока не понял что тут делать
        super.onCleared()
    }
}