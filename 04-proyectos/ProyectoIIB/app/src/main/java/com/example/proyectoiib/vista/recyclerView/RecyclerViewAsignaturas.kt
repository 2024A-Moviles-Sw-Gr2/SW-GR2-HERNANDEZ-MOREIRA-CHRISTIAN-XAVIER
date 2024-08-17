package com.example.proyectoiib.vista.recyclerView

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.asignatura.Asignatura
import com.example.proyectoiib.modelo.entidades.asignatura.TablaAsignatura
import com.example.proyectoiib.vista.actividadNota.ActividadNotaLayout
import com.example.proyectoiib.vista.asignatura.AsignaturasLayout

class RecyclerViewAsignaturas(
    private val contexto: AsignaturasLayout,
    private val lista: ArrayList<Asignatura>,
    private val recyclerView: RecyclerView
):RecyclerView.Adapter<RecyclerViewAsignaturas.MyViewHolder>() {

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val nombreAsignatura: TextView
        val profesorAsignatura: TextView
        val aulaAsignatura: TextView
        val botonEliminar: Button
        val frameAsignatura: FrameLayout

        init {
            nombreAsignatura = view.findViewById<TextView>(R.id.lb_asignatura)
            profesorAsignatura = view.findViewById<TextView>(R.id.lb_profesor)
            aulaAsignatura = view.findViewById<TextView>(R.id.lb_aula)
            botonEliminar = view.findViewById<Button>(R.id.btn_eliminarAsignatura)
            frameAsignatura = view.findViewById<FrameLayout>(R.id.fr_asignaturaLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_asignaturas, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tablaAsignatura = TablaAsignatura(contexto)
        val asignaturaActual = this.lista[position]
        holder.nombreAsignatura.text = asignaturaActual.nombre
        holder.profesorAsignatura.text = asignaturaActual.profesor
        holder.aulaAsignatura.text = asignaturaActual.aula

        //Evento para abrir la interfaz actividad-nota.
        holder.frameAsignatura.setOnClickListener {
            val intent = Intent(contexto, ActividadNotaLayout::class.java)
            intent.putExtra("id_asignatura", asignaturaActual.id)
            intent.putExtra("nombre_asignatura", asignaturaActual.nombre)
            intent.putExtra("profesor_asignatura", asignaturaActual.profesor)
            intent.putExtra("aula_asignatura", asignaturaActual.aula)

            contexto.startActivity(intent)
        }


        //Evento para eliminar la asignatura
        holder.botonEliminar.setOnClickListener {
            tablaAsignatura.eliminarAsignatura(asignaturaActual.id)
            contexto.inicializarRecyclerView()
        }

    }
}