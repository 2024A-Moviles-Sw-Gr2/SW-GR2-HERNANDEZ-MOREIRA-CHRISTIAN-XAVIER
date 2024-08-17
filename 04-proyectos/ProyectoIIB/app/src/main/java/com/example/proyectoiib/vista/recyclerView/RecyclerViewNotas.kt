package com.example.proyectoiib.vista.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoiib.R
import com.example.proyectoiib.modelo.entidades.nota.Nota
import com.example.proyectoiib.modelo.entidades.nota.TablaNota
import com.example.proyectoiib.vista.nota.FragmentoNotas

class RecyclerViewNotas(
    private val contexto: FragmentoNotas,
    private val lista: ArrayList<Nota>,
    private val id_asignatura: Int
): RecyclerView.Adapter<RecyclerViewNotas.MyViewHolder>()   {

    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tituloNota: TextView
        val descripcionNota: TextView
        val botonEliminar: Button

        init {
            tituloNota = view.findViewById<TextView>(R.id.lb_tituloNota)
            descripcionNota = view.findViewById<TextView>(R.id.lb_descripcionNota)
            botonEliminar = view.findViewById<Button>(R.id.btn_eliminarNota)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewNotas.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_notas, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: RecyclerViewNotas.MyViewHolder, position: Int) {
        val tablaNotas = TablaNota(contexto.context)
        val notaActual = tablaNotas.listarNotas(id_asignatura)[position]

        holder.tituloNota.text = notaActual.titulo
        holder.descripcionNota.text = notaActual.descripcion

        holder.botonEliminar.setOnClickListener {
            tablaNotas.eliminarNota(notaActual.id)
            lista.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }

    }


}