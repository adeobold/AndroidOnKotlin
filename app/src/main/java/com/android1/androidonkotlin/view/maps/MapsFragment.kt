package com.android1.androidonkotlin.view.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.android1.androidonkotlin.R
import com.android1.androidonkotlin.databinding.FragmentMapsBinding
import com.android1.androidonkotlin.databinding.FragmentMapsUiBinding
import com.android1.androidonkotlin.utils.REQUEST_CODE_LOCATION

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {

    lateinit var map: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val moscow = LatLng(55.755826, 37.617299900000035)
        googleMap.addMarker(MarkerOptions().position(moscow).title("Marker in Moscow"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(moscow))

        googleMap.setOnMapLongClickListener { latLng ->
            addMarkerToArray(latLng)
            setMarker(latLng, "", R.drawable.ic_map_marker)
            drawLine()
        }

        googleMap.uiSettings.isZoomControlsEnabled = true

        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
        } else {
            googleMap.isMyLocationEnabled = false
            googleMap.uiSettings.isMyLocationButtonEnabled = false
        }
    }

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    private fun checkPermission(permission: String): Boolean {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), permission)
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            return true
        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к локации")
                .setMessage("Для определения текущего местоположения предоставьте доступ к геолокации")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }
        return false
    }

    private var _binding: FragmentMapsUiBinding? = null
    private val binding: FragmentMapsUiBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsUiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonSearch.setOnClickListener {
            binding.searchAddress.text.toString().let { searchText ->
                val geocoder = Geocoder(requireContext())
                geocoder.getFromLocationName(searchText, 1)?.let { addressList ->
                    if (addressList.isNotEmpty()) {
                        val ln = LatLng(addressList.first().latitude, addressList.first().longitude)
                        setMarker(ln, searchText, R.drawable.ic_map_marker)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ln, 15f))
                    }else{
                        Toast.makeText(requireContext(), "По данному адресу ничего не найдено!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private val markers = mutableListOf<Marker>()
    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.ic_map_pin)
        markers.add(marker)
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(15f)
            )
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )!!
    }

}