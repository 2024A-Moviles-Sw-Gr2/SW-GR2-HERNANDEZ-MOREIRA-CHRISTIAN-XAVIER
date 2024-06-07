package com.example.a2024aswgr2cxhm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""

    fun mostrarSanckBar(texto:String) {
        textoGlobal += texto
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSanckBar("OnCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSanckBar("OnStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSanckBar("OnResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSanckBar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSanckBar("OnPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSanckBar("OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSanckBar("OnDestroy")
    }
}