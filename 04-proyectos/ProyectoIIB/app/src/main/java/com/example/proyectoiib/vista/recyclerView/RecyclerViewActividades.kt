package com.example.proyectoiib.vista.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.actividad.Actividad
import com.example.proyectoiib.modelo.entidades.actividad.TablaActividad
import com.example.proyectoiib.vista.actividad.FragmentoActividades

class RecyclerViewActividades(
    private val contexto: FragmentoActividades,
    private val lista: ArrayList<Actividad>,
    private val id_asignatura: Int
):RecyclerView.Adapter<RecyclerViewActividades.MyViewHolder>()  {
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tituloActividad: TextView
        val fechaActividad: TextView
        val descripcionActividad: TextView
        val botonEliminar: Button

        init {
            tituloActividad = view.findViewById<TextView>(R.id.lb_tituloActividad)
            fechaActividad = view.findViewById<TextView>(R.id.lb_fechaActividad)
            descripcionActividad = view.findViewById<TextView>(R.id.lb_descripcionActividad)
            botonEliminar = view.findViewById<Button>(R.id.btn_eliminarActividad)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewActividades.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_actividades, parent, false
        )
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tablaActividad = TablaActividad(contexto.context)
        val actividadActual = tablaActividad.listarActividades(id_asignatura)[position]

        holder.tituloActividad.text = actividadActual.titulo
        holder.fechaActividad.text = actividadActual.fechaLimite
        holder.descripcionActividad.text = actividadActual.descripcion

        holder.botonEliminar.setOnClickListener {
            tablaActividad.eliminarActividad(actividadActual.id)
            lista.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }

    }

}