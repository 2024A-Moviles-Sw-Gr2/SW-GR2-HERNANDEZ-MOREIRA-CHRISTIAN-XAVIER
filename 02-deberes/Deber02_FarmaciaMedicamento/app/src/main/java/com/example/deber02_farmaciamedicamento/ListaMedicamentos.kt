package com.example.deber02_farmaciamedicamento

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ListaMedicamentos : AppCompatActivity() {

    private var listaMedicamentos: ArrayList<Medicamento> = arrayListOf()
    private var adapter: ArrayAdapter<Medicamento>? = null
    private var posicion = -1
    private var farmaciaSeleccionada: Farmacia? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medicamentos)

        farmaciaSeleccionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("farmaciaSeleccionada", Farmacia::class.java)
        } else {
            intent.getParcelableExtra<Farmacia>("farmaciaSeleccionada")
        }
        val medicamentosFarmacia = findViewById<TextView>(R.id.titulo_Lista_Medicamentos)
        medicamentosFarmacia.text = farmaciaSeleccionada!!.nombreFarmacia

        DataBase.tables = SqliteHelper(this)
        val listaMedicamentosView = findViewById<ListView>(R.id.lista_Medicamentos)
        listaMedicamentos = DataBase.tables!!.obtenerMedicamentosPorFarmacia(farmaciaSeleccionada!!.idFarmacia)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaMedicamentos
        )

        listaMedicamentosView.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val irCrearMedicamento = findViewById<Button>(R.id.crear_Medicamento_directo)
        irCrearMedicamento.setOnClickListener {
            goToActivity(CrearEditarMedicamento::class.java, null, farmaciaSeleccionada!!)
        }
        val regresar = findViewById<Button>(R.id.regresar)
        regresar.setOnClickListener {
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(listaMedicamentosView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_medicamento, menu)

        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editarMedicamento -> {
                goToActivity(CrearEditarMedicamento::class.java, listaMedicamentos[posicion], farmaciaSeleccionada!!, false)
                true
            }
            R.id.borrarMedicamento -> {
                openDialog(listaMedicamentos[posicion].idMedicamento)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: Medicamento? = null,
        dataToPass2: Farmacia? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("medicamentoSeleccionado", dataToPass)
            }
        }
        if (dataToPass2 != null) {
            intent.apply {
                putExtra("farmaciaSeleccionada", dataToPass2)
            }
        }
        intent.apply {
            putExtra("create", create)
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar el medicamento?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.borrarMedicamento(registerIndex)
            listaMedicamentos.clear()
            listaMedicamentos.addAll(DataBase.tables!!.obtenerMedicamentosPorFarmacia(farmaciaSeleccionada!!.idFarmacia))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}
