package com.android1.androidonkotlin.view.weatherList

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android1.androidonkotlin.R
import com.android1.androidonkotlin.databinding.FragmentWeatherListBinding
import com.android1.androidonkotlin.domain.City
import com.android1.androidonkotlin.domain.WeatherItem
import com.android1.androidonkotlin.utils.REQUEST_CODE_LOCATION
import com.android1.androidonkotlin.utils.SP_DB_NAME_IS_RUSSIAN
import com.android1.androidonkotlin.utils.SP_KEY_IS_RUSSIAN
import com.android1.androidonkotlin.view.details.DetailsFragment
import com.android1.androidonkotlin.view.details.OnItemClick
import com.android1.androidonkotlin.viewmodel.citieslist.CitiesListFragmentAppState
import com.android1.androidonkotlin.viewmodel.citieslist.CitiesListViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.system.measureTimeMillis


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

        binding.weatherListFragmentFABCities.setOnClickListener {
            isLocal = !isLocal
            loadTownList()
            sp.edit().apply {
                putBoolean(SP_KEY_IS_RUSSIAN, isLocal)
                apply()
            }
        }

        binding.weatherListFragmentFABLocation.setOnClickListener{
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    private fun checkPermission(permission: String) {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), permission)
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к локации")
                .setMessage("Для отображения погоды по текущему местоположению предоставьте доступ к геолокации")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            for (pIndex in permissions.indices) {
                if (permissions[pIndex] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[pIndex] == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                    Log.d("@@@", "Ура")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    lateinit var locationManager: LocationManager

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0F, locationListener
                )
            }else{
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { getAddress(it) }
            }
        }
    }



    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("@@@", "${location.latitude} ${location.longitude}")
            getAddress(location)
        }

        override fun onProviderDisabled(provider: String) {
            Log.d("@@@", "onProviderDisabled")
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            Log.d("@@@", "onProviderEnabled")
            super.onProviderEnabled(provider)
        }

    }

    fun getAddress(location: Location) {
        val geocoder = Geocoder(context, Locale("ru_RU"))
        val time = measureTimeMillis {
            Thread{
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                locationManager.removeUpdates(locationListener)
                onItemClick(WeatherItem(City(address.first().locality,location.latitude, location.longitude)))
            }.start()
        }
        Log.d("@@@", "$time")
    }

    private fun loadTownList() {
        if (isLocal) {
            listViewModel.getWeatherListForRussia()
            binding.weatherListFragmentFABCities.setImageResource(R.drawable.ic_russia)
        } else {
            listViewModel.getWeatherListForWorld()
            binding.weatherListFragmentFABCities.setImageResource(R.drawable.ic_earth)
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
        this.weatherListFragmentFABCities.visibility = View.GONE
    }

    private fun FragmentWeatherListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFABCities.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: WeatherItem) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }


}