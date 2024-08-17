package com.example.deber02_farmaciamedicamento

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CrearEditarMedicamento : AppCompatActivity() {

    private lateinit var nombreMedicamento: EditText
    private lateinit var esRecetado: EditText
    private lateinit var precioMedicamento: EditText
    private lateinit var idFarmacia: EditText
    private lateinit var guardarMedicamento: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_medicamento)

        initializeViews()
        val medicamentoSeleccionado = getParcelableExtraCompat<Medicamento>("medicamentoSeleccionado")
        val farmaciaSeleccionada = getParcelableExtraCompat<Farmacia>("farmaciaSeleccionada")
        val create = intent.getBooleanExtra("create", true)

        if (create) {
            idFarmacia.setText(farmaciaSeleccionada?.idFarmacia.toString())
            guardarMedicamento.setOnClickListener {
                DataBase.tables?.crearMedicamento(
                    nombreMedicamento.text.toString(),
                    esRecetado.text.toString().toBoolean(),
                    precioMedicamento.text.toString().toDouble(),
                    idFarmacia.text.toString().toInt()
                )
                if (farmaciaSeleccionada != null) {
                    goToActivity(ListaMedicamentos::class.java, farmaciaSeleccionada)
                }
            }
        } else {
            medicamentoSeleccionado?.let {
                nombreMedicamento.setText(it.nombreMedicamento)
                esRecetado.setText(it.esRecetado.toString())
                precioMedicamento.setText(it.precioMedicamento.toString())
                idFarmacia.setText(it.idFarmacia.toString())
            }
            guardarMedicamento.setOnClickListener {
                medicamentoSeleccionado?.let {
                    DataBase.tables?.actualizarMedicamento(
                        it.idMedicamento,
                        nombreMedicamento.text.toString(),
                        esRecetado.text.toString().toBoolean(),
                        precioMedicamento.text.toString().toDouble(),
                        idFarmacia.text.toString().toInt()
                    )
                }
                goToActivity(ListaMedicamentos::class.java, farmaciaSeleccionada!!)
            }
        }
    }

    private fun initializeViews() {
        nombreMedicamento = findViewById(R.id.nombre_Medicamento)
        esRecetado = findViewById(R.id.esRecetado_Medicamento)
        precioMedicamento = findViewById(R.id.precio_Medicamento)
        idFarmacia = findViewById(R.id.id_Farmacia)
        guardarMedicamento = findViewById(R.id.guardar_Medicamento)
    }

    private inline fun <reified T : Parcelable> getParcelableExtraCompat(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }

    private fun goToActivity(activityClass: Class<*>, dataToPass: Farmacia) {
        val intent = Intent(this, activityClass).apply {
            putExtra("farmaciaSeleccionada", dataToPass)
        }
        startActivity(intent)
    }
}
