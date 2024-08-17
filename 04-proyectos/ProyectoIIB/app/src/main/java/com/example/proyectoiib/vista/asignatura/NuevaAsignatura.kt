package com.example.proyectoiib.vista.asignatura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.asignatura.TablaAsignatura

class NuevaAsignatura : AppCompatActivity() {
    val tablaAsignatura = TablaAsignatura(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_asignatura)

        val botonAceptar = findViewById<Button>(R.id.btn_aceptarAgregarAsignatura)
        botonAceptar.setOnClickListener {
            val nombre = findViewById<TextView>(R.id.txt_nombreAsignatura).text.toString()
            val profesor = findViewById<TextView>(R.id.txt_profesorAsignatura).text.toString()
            val aula = findViewById<TextView>(R.id.txt_aulaAsignatura).text.toString()

            tablaAsignatura.agregarAsignatura(
                nombre,
                profesor,
                aula
            )
            devolverRespuesta()
        }

        val botonCancelar = findViewById<Button>(R.id.btn_cancelarAgregarAsignatura)
        botonCancelar.setOnClickListener {
            finish()
        }

    }

    fun devolverRespuesta(){
        val intentDevolverRespuesta = Intent()
        setResult(RESULT_OK, intentDevolverRespuesta)
        finish()
    }

}