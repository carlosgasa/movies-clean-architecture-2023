package com.gscarlos.moviescleanarchitecture.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.databinding.FragmentLocationsBinding
import com.gscarlos.moviescleanarchitecture.ui.locations.adapter.LocationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint

class LocationsFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: LocationsViewModel by viewModels()
    private lateinit var mAdapter: LocationsAdapter
    private lateinit var map: GoogleMap

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUiState()
        viewModel.loadLocationsFromFirebase()
    }

    private fun initUiState() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.locationsState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {

                    when (it) {
                        LocationsViewState.Start -> {}
                        LocationsViewState.Error -> {
                            binding.tvEmptyData.visibility = View.VISIBLE
                        }
                        LocationsViewState.Loading -> {
                            binding.pbLoading.visibility = View.VISIBLE
                            binding.tvEmptyData.visibility = View.GONE
                        }
                        is LocationsViewState.Success -> {
                            binding.pbLoading.visibility = View.GONE
                            binding.tvEmptyData.visibility = View.GONE

                            mAdapter.setList(it.locations)

                            if (it.locations.isNotEmpty()) {

                                binding.rvLocations.visibility = View.VISIBLE
                                binding.tvEmptyData.visibility = View.GONE

                                it.locations.forEach { l ->
                                    map.addMarker(
                                        MarkerOptions().position(LatLng(l.latitud, l.longitud))
                                            .title(
                                                l.fecha.toString()
                                            )
                                    )
                                }
                                map.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            it.locations.first().latitud,
                                            it.locations.first().longitud
                                        ), 16f
                                    ),
                                    3000,
                                    null
                                )
                            } else {
                                binding.rvLocations.visibility = View.GONE
                                binding.tvEmptyData.visibility = View.VISIBLE
                            }
                        }
                    }
                }
        }
    }

    private fun initComponents() {
        mAdapter = LocationsAdapter {
            val center = CameraUpdateFactory.newLatLng(LatLng(it.latitud, it.longitud))
            val zoom = CameraUpdateFactory.zoomTo(18f)

            map.moveCamera(center)
            map.animateCamera(zoom)
        }
        binding.rvLocations.setHasFixedSize(true)
        binding.rvLocations.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvLocations.adapter = mAdapter

        val mapFragment = childFragmentManager.findFragmentById(R.id.fMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }
}