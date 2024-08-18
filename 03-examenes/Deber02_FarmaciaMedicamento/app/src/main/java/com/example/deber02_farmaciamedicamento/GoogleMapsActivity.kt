package com.example.deber02_farmaciamedicamento

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var farmacias: ArrayList<Farmacia> = arrayListOf()
    private var currentFarmaciaIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        // Inicializar las farmacias
        farmacias = DataBase.tables!!.obtenerTodasFarmacias()

        // Configurar el fragmento de mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configurar el botón para navegar entre farmacias
        val botonNextFarmacia = findViewById<Button>(R.id.btn_next_farmacia)
        botonNextFarmacia.setOnClickListener {
            currentFarmaciaIndex = (currentFarmaciaIndex + 1) % farmacias.size
            mostrarUbicacionFarmacia(farmacias[currentFarmaciaIndex])
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Mostrar la primera farmacia en el mapa
        if (farmacias.isNotEmpty()) {
            mostrarUbicacionFarmacia(farmacias[currentFarmaciaIndex])
        }
    }

    private fun mostrarUbicacionFarmacia(farmacia: Farmacia) {
        val ubicacion = LatLng(farmacia.latitudFarmacia, farmacia.longitudFarmacia)
        map.clear()
        map.addMarker(MarkerOptions().position(ubicacion).title(farmacia.nombreFarmacia))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
    }
}
