package com.gscarlos.moviescleanarchitecture.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gscarlos.moviescleanarchitecture.R
import com.gscarlos.moviescleanarchitecture.data.remote.location.GPSService
import com.gscarlos.moviescleanarchitecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_profile, R.id.navigation_movies, R.id.navigation_locations, R.id.navigation_images
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        initServiceGPS()
    }

    private fun initServiceGPS() {
        //Checar permisos para inicializar el servicio de gps
        if(!checkPermission()){
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.txt_attention))
                .setMessage(getString(R.string.txt_request_to_use))
                .setPositiveButton(getString(R.string.txt_understand)) { _, _ ->
                    requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                }
                .setNegativeButton(getString(R.string.txt_cancel)) { _, _ ->
                    Toast.makeText(this, R.string.txt_no_permission, Toast.LENGTH_SHORT).show()
                }
                .show()
        } else {
            startGPSService()
        }
    }

    private fun startGPSService() {
        ContextCompat.startForegroundService(this, Intent(this, GPSService::class.java))
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)
        val result1 = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var isGranted = true
        permissions.entries.forEach {
            isGranted = isGranted && it.value
        }
        if (isGranted) {
            startGPSService()
        } else {
            Toast.makeText(this, "R.string.txt_no_permission", Toast.LENGTH_SHORT).show()
        }
    }
}