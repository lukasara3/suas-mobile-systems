package com.example.tutorial6_ex2

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), LocationListener {

    @SuppressLint("SetTextI18n")
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions: Map<String, Boolean> ->
        val isGranted = permissions.values.any { it }

        if (isGranted) {
            @SuppressLint("MissingPermission")
            getLocation()
        } else {
            val showRationale = shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)

            if (!showRationale) {
                AlertDialog.Builder(this)
                    .setTitle("Permissão Necessária")
                    .setMessage("As permissões foram negadas permanentemente. A aplicação precisa delas para funcionar. Por favor, ative-as nas Definições do telemóvel.")
                    .setPositiveButton("Ir para Definições") { dialog, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = android.net.Uri.fromParts("package", packageName, null)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            } else {
                coordinates.text = "Permissões foram negadas."
            }
        }
    }

    private lateinit var nameCity: TextView
    private lateinit var coordinates: TextView
    private lateinit var locationManager: LocationManager

    @SuppressLint("SetTextI18n")
    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    fun getLocation() {
        val permission1 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        val permission2 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)

        if (permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED) {
            val isGpsEnabled = locationManager.isProviderEnabled(GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(NETWORK_PROVIDER)

            val providerString = if (isGpsEnabled) {
                GPS_PROVIDER
            } else if (isNetworkEnabled) {
                NETWORK_PROVIDER
            } else {
                null
            }

            if (providerString != null) {
                val cacheLocation = locationManager.getLastKnownLocation(providerString)

                if (cacheLocation != null) {
                    onLocationChanged(cacheLocation)
                }

                locationManager.requestLocationUpdates(
                    providerString,
                    1000L,
                    1f,
                    this
                )
            } else {
                coordinates.text = "Erro: GPS e Internet desligados"
                nameCity.text = "Ative a localização"
            }
        } else {
            val showRationale =
                shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)

            if (showRationale) {
                AlertDialog.Builder(this)
                    .setTitle("Permissão de Localização")
                    .setMessage("A aplicação precisa de aceder à sua localização para conseguir calcular as coordenadas e encontrar a cidade mais próxima. Por favor, conceda a permissão no próximo ecrã.")
                    .setPositiveButton("Entendi") { dialog, _ ->
                        requestPermissionLauncher.launch(
                            arrayOf(
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            } else {
                coordinates.text = "Sem permissão de localização"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        nameCity = findViewById(R.id.nameCity)
        coordinates = findViewById(R.id.coordinates)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(location: Location) {
        coordinates.text = "Lat: ${location.latitude}\nLng: ${location.longitude}"
        val geocoder = Geocoder(this,java.util.Locale.getDefault())

        geocoder.getFromLocation(location.latitude, location.longitude, 1,
            Geocoder.GeocodeListener { addresses ->
                if (addresses.isNotEmpty()) {
                    val cityName = addresses[0].locality
                    runOnUiThread {
                        nameCity.text = cityName ?: "Cidade desconhecida"
                    }
                }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(this)
    }

    @RequiresPermission(allOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    override fun onResume() {
        super.onResume()
        getLocation()
    }
}