package com.example.deber02_farmaciamedicamento

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CrearEditarFarmacia : AppCompatActivity() {

    private lateinit var nombreFarmacia: EditText
    private lateinit var direccionFarmacia: EditText
    private lateinit var numeroTotalMedicamentos: EditText
    private lateinit var abierta24Horas: EditText
    private lateinit var guardarFarmacia: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_farmacia)

        initializeViews()
        val farmaciaSeleccionada = getParcelableExtraCompat<Farmacia>("farmaciaSeleccionada")

        if (farmaciaSeleccionada != null) {
            populateFields(farmaciaSeleccionada)
            guardarFarmacia.setOnClickListener {
                DataBase.tables?.actualizarFarmacia(
                    farmaciaSeleccionada.idFarmacia,
                    nombreFarmacia.text.toString(),
                    direccionFarmacia.text.toString(),
                    numeroTotalMedicamentos.text.toString().toInt(),
                    abierta24Horas.text.toString().toBoolean()
                )
                goToActivity(MainActivity::class.java)
            }
        } else {
            guardarFarmacia.setOnClickListener {
                DataBase.tables?.crearFarmacia(
                    nombreFarmacia.text.toString(),
                    direccionFarmacia.text.toString(),
                    numeroTotalMedicamentos.text.toString().toInt(),
                    abierta24Horas.text.toString().toBoolean()
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun initializeViews() {
        nombreFarmacia = findViewById(R.id.nombre_Farmacia)
        direccionFarmacia = findViewById(R.id.direccion_Farmacia)
        numeroTotalMedicamentos = findViewById(R.id.numeroTotal_Medicamentos)
        abierta24Horas = findViewById(R.id.abierta24Horas_Farmacia)
        guardarFarmacia = findViewById(R.id.guardar_Farmacia)
    }

    private fun populateFields(farmacia: Farmacia) {
        nombreFarmacia.setText(farmacia.nombreFarmacia)
        direccionFarmacia.setText(farmacia.direccionFarmacia)
        numeroTotalMedicamentos.setText(farmacia.numeroTotalMedicamentos.toString())
        abierta24Horas.setText(farmacia.abierta24Horas.toString())
    }

    private inline fun <reified T : Parcelable> getParcelableExtraCompat(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }

    private fun goToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
