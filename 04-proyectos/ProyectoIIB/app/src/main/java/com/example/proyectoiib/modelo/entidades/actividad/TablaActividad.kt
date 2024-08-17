package com.example.proyectoiib.modelo.entidades.actividad

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.proyectoiib.modelo.baseDatos.SQLiteHelper


class TablaActividad(contexto: Context?):SQLiteHelper(contexto) {
    fun agregarActividad(
        titulo: String,
        fechaLimite: String,
        descripcion: String,
        idAsignatura: Int
    ){
        val conexionEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("titulo", titulo)
        valoresGuardar.put("fechaLimite", fechaLimite)
        valoresGuardar.put("descripcion", descripcion)
        valoresGuardar.put("id_asignatura", idAsignatura)
        conexionEscritura.insert("ACTIVIDAD", null, valoresGuardar)
    }

    fun eliminarActividad(idActividad: Int){
        val conexionEscritura = writableDatabase
        val consultaEliminar = arrayOf(idActividad.toString())
        conexionEscritura.delete("ACTIVIDAD", "id=?", consultaEliminar)
        conexionEscritura.close()
    }

    fun listarActividades(idAsignatura: Int): ArrayList<Actividad> {
        val arregloActividades = ArrayList<Actividad>()

        var resultadoConsultaListar: Cursor

        val conexionLectura = readableDatabase
        var scriptConsulta =
            """
                SELECT * FROM ACTIVIDAD WHERE id_asignatura = ?
            """.trimIndent()

        resultadoConsultaListar = conexionLectura.rawQuery(scriptConsulta, arrayOf(idAsignatura.toString()))


        val existeUno = resultadoConsultaListar.moveToFirst()

        if (existeUno){
            do {
                val actividad = Actividad(
                    resultadoConsultaListar.getInt(0),
                    resultadoConsultaListar.getString(1),
                    resultadoConsultaListar.getString(2),
                    resultadoConsultaListar.getString(3)
                )
                arregloActividades.add(actividad)
            }while (resultadoConsultaListar.moveToNext())
        }
        resultadoConsultaListar.close()
        conexionLectura.close()
        return arregloActividades
    }
}