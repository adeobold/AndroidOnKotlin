package com.android1.androidonkotlin.view.weatherHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android1.androidonkotlin.databinding.FragmentWeatherHistoryListBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.view.details.OnItemClick
import com.android1.androidonkotlin.viewmodel.weatherHistory.WeatherHistoryListFragmentAppState
import com.android1.androidonkotlin.viewmodel.weatherHistory.WeatherHistoryListViewModel

class WeatherHistoryListFragment: Fragment(), OnItemClick {

    private var _binding: FragmentWeatherHistoryListBinding? = null
    private val binding: FragmentWeatherHistoryListBinding
        get() {
            return _binding!!
        }

    override fun onItemClick(weather: WeatherItem) {

    }

    lateinit var viewModel: WeatherHistoryListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherHistoryListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherHistoryListViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }
        viewModel.getAllHistory()
    }

    private fun renderData(weatherHistoryListFragmentAppState: WeatherHistoryListFragmentAppState) {
        when (weatherHistoryListFragmentAppState) {
            is WeatherHistoryListFragmentAppState.Error -> {
            }
            WeatherHistoryListFragmentAppState.Loading -> {
            }
            is WeatherHistoryListFragmentAppState.SuccessMulti -> {
                binding.historyFragmentRecyclerView.adapter =
                    WeatherHistoryListAdapter(weatherHistoryListFragmentAppState.weatherList, this)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}