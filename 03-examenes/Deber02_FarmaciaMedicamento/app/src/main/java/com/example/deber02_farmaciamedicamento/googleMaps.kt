package com.example.deber02_farmaciamedicamento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.deber02_farmaciamedicamento.databinding.ActivityGoogleMapsBinding

class googleMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding
    private var farmacias: List<Farmacia> = emptyList()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGoogleMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Obtener las farmacias almacenadas desde la base de datos
        val dbHelper = SqliteHelper(this)
        farmacias = dbHelper.obtenerTodasFarmacias()

        binding.btnIrSiguiente.setOnClickListener {
            irALaSiguienteUbicacion()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Mover el mapa a la ubicación de la primera farmacia si existen farmacias
        if (farmacias.isNotEmpty()) {
            moverMapaAFarmacia(farmacias[currentIndex])
        } else {
            Toast.makeText(this, "No existen farmacias almacenadas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moverMapaAFarmacia(farmacia: Farmacia) {
        val coordenadas = LatLng(farmacia.latitudFarmacia, farmacia.longitudFarmacia)
        mMap.clear() // Limpiar los marcadores anteriores
        mMap.addMarker(MarkerOptions().position(coordenadas).title("Ubicación: ${farmacia.nombreFarmacia}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 10f))
    }

    private fun irALaSiguienteUbicacion() {
        if (farmacias.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % farmacias.size
            val farmacia = farmacias[currentIndex]
            moverMapaAFarmacia(farmacia)
        } else {
            Toast.makeText(this, "No existen farmacias almacenadas", Toast.LENGTH_SHORT).show()
        }
    }
}