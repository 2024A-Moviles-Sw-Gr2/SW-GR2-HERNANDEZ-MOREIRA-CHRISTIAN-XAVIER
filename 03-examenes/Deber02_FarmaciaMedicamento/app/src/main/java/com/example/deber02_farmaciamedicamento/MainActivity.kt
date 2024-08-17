package com.example.deber02_farmaciamedicamento

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private var farmacias: ArrayList<Farmacia> = arrayListOf()
    private var adapter: ArrayAdapter<Farmacia>? = null
    private var posicion = -1

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBase.tables = SqliteHelper(this)
        val listaFarmacias = findViewById<ListView>(R.id.lista_Farmacias)
        farmacias = DataBase.tables!!.obtenerTodasFarmacias()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            farmacias
        )

        listaFarmacias.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val irCrearFarmacia = findViewById<Button>(R.id.agregar_Farmacia)
        irCrearFarmacia.setOnClickListener {
            goToActivity(CrearEditarFarmacia::class.java)
        }

        val botonMapa = findViewById<Button>(R.id.btn_ir_mapa)
        botonMapa.setOnClickListener {
            goToActivity(googleMaps::class.java)
        }

            registerForContextMenu(listaFarmacias)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_farmacia, menu)

        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_Farmacia -> {
                goToActivity(CrearEditarFarmacia::class.java, farmacias[posicion])
                true
            }
            R.id.borrar_Farmacia -> {
                openDialog(farmacias[posicion].idFarmacia)
                true
            }
            R.id.ver_Medicamentos -> {
                goToActivity(ListaMedicamentos::class.java, farmacias[posicion])
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: Farmacia? = null
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("farmaciaSeleccionada", dataToPass)
            }
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar la farmacia?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            DataBase.tables!!.borrarFarmacia(registerIndex)
            farmacias.clear()
            farmacias.addAll(DataBase.tables!!.obtenerTodasFarmacias())
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }
}
