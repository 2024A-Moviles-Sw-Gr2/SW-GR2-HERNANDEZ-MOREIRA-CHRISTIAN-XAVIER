package com.example.proyectoiib.vista.nota

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
import com.example.proyectoiib.modelo.entidades.nota.TablaNota
import com.example.proyectoiib.vista.actividad.FragmentoActividades
import com.example.proyectoiib.vista.actividad.NuevaActividad
import com.example.proyectoiib.vista.recyclerView.RecyclerViewActividades
import com.example.proyectoiib.vista.recyclerView.RecyclerViewNotas

class FragmentoNotas : Fragment() {

    private var id_asignatura: Int = 0
    private lateinit var rootView: View

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_fragmento_notas, container, false)
        inicializarRecyclerView(rootView)

        val botonAgregarNotas = rootView.findViewById<Button>(R.id.btn_agregarNota)

        botonAgregarNotas.setOnClickListener {
            val intent = Intent(rootView.context, NuevaNota::class.java)
            intent.putExtra("id_asignatura", id_asignatura)
            callbackContenidoIntentExplicito.launch(intent)
        }

        return rootView
    }

    fun inicializarRecyclerView(view: View){
        val tablaNotas = TablaNota(context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_notas)
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val adaptador = RecyclerViewNotas(
            this,
            tablaNotas.listarNotas(id_asignatura),
            id_asignatura
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        adaptador.notifyDataSetChanged()

    }


    companion object {
        fun newInstance(idAsignatura: Int): FragmentoNotas {
            val fragment = FragmentoNotas()
            val args = Bundle()
            args.putInt("id_asignatura", idAsignatura)
            fragment.arguments = args
            return fragment
        }
    }
}