package com.example.a2024aswgr2cxhm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView.FindListener
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    fun mostrarSnackBar(texto:String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_sqlite),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        // Buscar Entrenador
        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EBaseDeDatos.tablaEntrenador!!
                .consultarEntrenadorPorID(
                    id.text.toString().toInt()
                )
            if(entrenador == null){
                mostrarSnackBar("Usu. no encontrado")
                id.setText("")
                nombre.setText("")
                descripcion.setText("")
            } else {
                id.setTag(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
                mostrarSnackBar("Usu. encontrado")
            }
        }

        // Boton Crear
        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!
                .crearEntrenador(
                    nombre.text.toString(),
                    descripcion.text.toString()
                )
            if (respuesta) mostrarSnackBar("Entr. creado!")
        }

        // Boton Actualizar
        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonCrearBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!
                .actualizarEntrenadorFormulario(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    id.text.toString().toInt()
                )
            if (respuesta) mostrarSnackBar("Entr. actualizado!")
        }

        // Boton Eliminar
        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.tablaEntrenador!!
                .eliminarEntrenadorFormulario(
                    id.text.toString().toInt()
                )
            if (respuesta) mostrarSnackBar("Entr. eliminado!")
        }
    }
}