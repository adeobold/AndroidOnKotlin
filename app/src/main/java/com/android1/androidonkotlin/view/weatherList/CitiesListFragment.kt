package com.android1.androidonkotlin.view.weatherList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android1.androidonkotlin.R
import com.android1.androidonkotlin.databinding.FragmentWeatherListBinding
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.utils.SP_DB_NAME_IS_RUSSIAN
import com.android1.androidonkotlin.utils.SP_KEY_IS_RUSSIAN
import com.android1.androidonkotlin.view.details.DetailsFragment
import com.android1.androidonkotlin.view.details.OnItemClick
import com.android1.androidonkotlin.viewmodel.citieslist.CitiesListFragmentAppState
import com.android1.androidonkotlin.viewmodel.citieslist.CitiesListViewModel
import com.google.android.material.snackbar.Snackbar


class CitiesListFragment : Fragment(), OnItemClick {

    private var isLocal = true

    companion object {
        fun newInstance() = CitiesListFragment()
    }

    private lateinit var listViewModel: CitiesListViewModel

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel = ViewModelProvider(this).get(CitiesListViewModel::class.java)

        val sp = requireActivity().getSharedPreferences(SP_DB_NAME_IS_RUSSIAN, Context.MODE_PRIVATE)
        isLocal = sp.getBoolean(SP_KEY_IS_RUSSIAN, true)
        loadTownList()

        listViewModel.getLiveData().observe(viewLifecycleOwner) { t -> renderData(t) }

        binding.weatherListFragmentFAB.setOnClickListener {
            isLocal = !isLocal
            loadTownList()
            sp.edit().apply {
                putBoolean(SP_KEY_IS_RUSSIAN, isLocal)
                apply()
            }
        }

    }

    private fun loadTownList() {
        if (isLocal) {
            listViewModel.getWeatherListForRussia()
            binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_russia)
        } else {
            listViewModel.getWeatherListForWorld()
            binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
    }

    private fun renderData(citiesListFragmentAppState: CitiesListFragmentAppState) {
        when (citiesListFragmentAppState) {
            is CitiesListFragmentAppState.Error -> {
                binding.showResult()
                binding.root.showSnackBar(
                    "Ошибка загрузки",
                    Snackbar.LENGTH_INDEFINITE,
                    "Попробовать еще раз"
                ) {
                    loadTownList()
                }
            }
            CitiesListFragmentAppState.Loading -> {
                binding.loading()
            }
            is CitiesListFragmentAppState.SuccessOne -> {
                binding.showResult()
            }
            is CitiesListFragmentAppState.SuccessMulti -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    CitiesListAdapter(citiesListFragmentAppState.weatherList, this)
            }
        }
    }

    private fun View.showSnackBar(
        string: String,
        duration: Int,
        actionString: String,
        block: (v: View) -> Unit
    ) {
        Snackbar.make(this, string, duration).setAction(actionString, block).show()
    }

    private fun FragmentWeatherListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    private fun FragmentWeatherListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: WeatherItem) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }


}