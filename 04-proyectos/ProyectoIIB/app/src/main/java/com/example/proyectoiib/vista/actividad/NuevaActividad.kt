package com.example.proyectoiib.vista.actividad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.actividad.TablaActividad

class NuevaActividad : AppCompatActivity() {

    val tablaActividad = TablaActividad(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_actividad)

        //Rescate del id_asignatura transmitido.
        val idAsignatura = intent.getIntExtra("id_asignatura", -1)

        //Evento para finalizar la actividad.
        val botonCancelarAgregar = findViewById<Button>(R.id.btn_cancelarAgregarActividad)
        botonCancelarAgregar.setOnClickListener {
            finish()
        }

        //Evento que rescata los datos, y los guarda en la tabla de la BD.
        val botonAceptarAgregar = findViewById<Button>(R.id.btn_aceptarAgregarActividad)
        botonAceptarAgregar.setOnClickListener {
            val tituloActividad = findViewById<TextView>(R.id.txt_tituloActividadAgregar).text.toString()
            val fechaActividad = findViewById<TextView>(R.id.txt_fechaActividadAgregar).text.toString()
            val descripcionActividad = findViewById<TextView>(R.id.txt_descripcionActividadAgregar).text.toString()

            tablaActividad.agregarActividad(
                tituloActividad,
                fechaActividad,
                descripcionActividad,
                idAsignatura
            )

            devolverRespuesta()

        }

    }

    fun devolverRespuesta(){
        val intentDevolverRespuesta = Intent()
        setResult(RESULT_OK, intentDevolverRespuesta)
        finish()
    }
}