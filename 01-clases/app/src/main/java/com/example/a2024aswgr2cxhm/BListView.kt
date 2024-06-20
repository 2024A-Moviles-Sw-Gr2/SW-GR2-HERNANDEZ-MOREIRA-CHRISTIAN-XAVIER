package com.example.a2024aswgr2cxhm

import android.content.DialogInterface
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
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this, //Contexto
        android.R.layout.simple_list_item_1, //Layout XML a usar
        arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() //Actualiza UI (muy importante)
        val botonAnadirListView = findViewById<Button>(
            R.id.btn_anadir_list_view
        )
        botonAnadirListView.setOnClickListener {
            anadirEntrenador(adaptador)
        }
        registerForContextMenu(listView) //Nueva línea
    } //Termina OnCreate

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu (
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos opciones del menu
        var inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //Obtener id
        val info =menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(
        item: MenuItem
    ): Boolean {
        return when (item.itemId){
            R.id.mi_editar -> {
                mostrarSanckbar(
                    "Editar $posicionItemSeleccionado")
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSanckbar(
                    "Eliminar $posicionItemSeleccionado")
                abrirDialogo() //Nueva línea
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                dialog, which ->
            mostrarSanckbar("Acepto $which")
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val opciones = resources.getStringArray(
            R.array.string_array_opciones
        )
        val seleccionPrevia = booleanArrayOf(
            true, // Lunes,
            false, //Martes,
            false, //Miércoles
        )

        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia
        ) { dialog, which, isChecked ->
            mostrarSanckbar("Item: $which")
        }
        val dialog = builder.create()
        dialog.show()
    }
    fun anadirEntrenador (adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(4, "Wendy", "d@d.com")
        )
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSanckbar (texto:String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_blist_view),
            texto,
        Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}