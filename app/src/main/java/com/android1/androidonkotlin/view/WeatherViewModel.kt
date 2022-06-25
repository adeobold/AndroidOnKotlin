package com.android1.androidonkotlin.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android1.androidonkotlin.model.Repository
import com.android1.androidonkotlin.model.RepositoryLocalImpl
import com.android1.androidonkotlin.model.RepositoryRemoteImpl
import com.android1.androidonkotlin.viewmodel.AppState

class WeatherViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) :
    ViewModel() {

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

    fun sentRequest() {
        //choiceRepository()
        liveData.value = AppState.Loading
        if ((0..3).random() == 2) { //FIXME
            liveData.postValue(AppState.Error(throw IllegalStateException("что-то пошло не так")))
        } else {
            liveData.postValue(
                AppState.Success(
                    repository.getWeather(
                        55.755826,
                        37.617299900000035
                    )
                )
            )
        }

    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() { // TODO HW ***
        super.onCleared()
    }
}