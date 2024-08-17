package com.example.proyectoiib.vista.nota

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.nota.TablaNota

class NuevaNota : AppCompatActivity() {

    val tablaNota = TablaNota(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_nota)

        val idAsignatura = intent.getIntExtra("id_asignatura", -1)


        val botonCancelarAgregar = findViewById<Button>(R.id.btn_cancelarAgregarNota)
        botonCancelarAgregar.setOnClickListener {
            finish()
        }

        val botonAceptarAgregar = findViewById<Button>(R.id.btn_aceptarAgregarNota)
        botonAceptarAgregar.setOnClickListener {
            val tituloNota = findViewById<TextView>(R.id.txt_tituloNotaAgregar).text.toString()
            val descripcionNota = findViewById<TextView>(R.id.txt_descripcionNotaAgregar).text.toString()

            tablaNota.agregarNota(
                tituloNota,
                descripcionNota,
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