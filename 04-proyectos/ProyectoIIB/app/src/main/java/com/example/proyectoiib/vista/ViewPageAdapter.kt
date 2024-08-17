package com.example.proyectoiib.vista

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.proyectoiib.vista.actividad.FragmentoActividades
import com.example.proyectoiib.vista.nota.FragmentoNotas

class ViewPageAdapter(activity: FragmentActivity, var id_asignatura:Int) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    //Creación de los fragmentos
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentoActividades.newInstance(id_asignatura)
            1 -> FragmentoNotas.newInstance(id_asignatura)
            else -> Fragment()
        }
    }

}