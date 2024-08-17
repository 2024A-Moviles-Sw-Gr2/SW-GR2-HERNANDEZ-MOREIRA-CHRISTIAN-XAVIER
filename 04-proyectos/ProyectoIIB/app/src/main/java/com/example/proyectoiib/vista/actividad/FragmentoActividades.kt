package com.example.proyectoiib.vista.actividad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.actividad.TablaActividad
import com.example.proyectoiib.vista.recyclerView.RecyclerViewActividades

class FragmentoActividades() : Fragment() {


    private var id_asignatura: Int = 0
    private lateinit var rootView: View

    //Rescate del id_asignatura transmitido.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id_asignatura = arguments?.getInt("id_asignatura") ?: 0
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    inicializarRecyclerView(rootView)
                }
            }
        }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        //Inicialización del view.
        rootView = inflater.inflate(R.layout.fragment_fragmento_actividades, container, false)
        inicializarRecyclerView(rootView)

        //Evento para abrir la interfaz para agregar una actividad.
        val botonAgregarActividad = rootView.findViewById<Button>(R.id.btn_agregarActividad)
        botonAgregarActividad.setOnClickListener {
            val intent = Intent(rootView.context, NuevaActividad::class.java)
            intent.putExtra("id_asignatura", id_asignatura)
            callbackContenidoIntentExplicito.launch(intent)
        }

        return rootView
    }

    fun inicializarRecyclerView(view: View){
        val tablaActividad = TablaActividad(context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_actividades)
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val adaptador = RecyclerViewActividades(
            this,
            tablaActividad.listarActividades(id_asignatura),
            id_asignatura
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        adaptador.notifyDataSetChanged()

    }


    //Creación de un nuevo fragmento.
    companion object {
        fun newInstance(idAsignatura: Int): FragmentoActividades {
            val fragment = FragmentoActividades()
            val args = Bundle()
            args.putInt("id_asignatura", idAsignatura)
            fragment.arguments = args
            return fragment
        }
    }

}