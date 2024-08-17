package com.example.proyectoiib.vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.proyectoiib.R
import com.example.proyectoiib.vista.asignatura.AsignaturasLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonIniciar = findViewById<Button>(R.id.btn_iniciarApp)
        botonIniciar.setOnClickListener {
            val intent = Intent(this, AsignaturasLayout::class.java)
            startActivity(intent)
        }

        val botonSalir = findViewById<Button>(R.id.btn_salirApp)
        botonSalir.setOnClickListener {
            finish()
        }

    }
}