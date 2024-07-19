package com.example.a2024aswgr2cxhm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    fun mostrarSnackBar(texto:String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Lógica Negocio
                    val data = result.data
                    mostrarSnackBar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }

    val callbackContenidoIntentImplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    // Lógica Negocio
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null, null
                        )
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackBar("Telefono $telefono")
                    }
                }
            }
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonCicloVida
            .setOnClickListener {
                irActividad(ACicloVida::class.java)
            }
        val botonIrListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botonIrListView
            .setOnClickListener{
                irActividad(BListView::class.java)
            }
        val botonIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito
        )
        botonIntentImplicito
            .setOnClickListener{
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackContenidoIntentImplicito.launch(intentConRespuesta)
            }

        val botonIntentExplicito = findViewById<Button>(
            R.id.btn_ir_intent_explicito
        )
        botonIntentExplicito
            .setOnClickListener{
                val intentExplicito = Intent(
                    this,
                    CIntentExplicitoParametros::class.java
                )
                intentExplicito.putExtra("nombre", "Christian")
                intentExplicito.putExtra("apellido", "Hernández")
                intentExplicito.putExtra("edad", 22)
                intentExplicito.putExtra(
                    "entrenador",
                    BEntrenador(10, "Jose", "Perez")
                )
                callbackContenidoIntentExplicito.launch(intentExplicito)
            }
        // Inicializar Base de Datos
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(
            this
        )
        val botonSqLite = findViewById<Button>(R.id.btn_sqlite)
        botonSqLite.setOnClickListener {
            irActividad(ECrudEntrenador::class.java)
        }
        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView.setOnClickListener {
            irActividad(FRecyclerView::class.java)
        }
    }

    fun irActividad (
        clase: Class<*>
    ) {
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}