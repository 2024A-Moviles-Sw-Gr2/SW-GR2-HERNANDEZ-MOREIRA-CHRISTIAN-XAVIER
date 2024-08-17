package com.example.proyectoiib.modelo.entidades.nota

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.proyectoiib.modelo.baseDatos.SQLiteHelper

class TablaNota(contexto: Context?): SQLiteHelper(contexto)  {
    fun agregarNota(
        titulo: String,
        descripcion: String,
        id_asignatura: Int
    ){
        val conexionEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("titulo", titulo)
        valoresGuardar.put("descripcion", descripcion)
        valoresGuardar.put("id_asignatura", id_asignatura)
        conexionEscritura.insert("NOTA", null, valoresGuardar)
    }

    fun eliminarNota(idNota: Int){
        val conexionEscritura = writableDatabase
        val consultaEliminar = arrayOf(idNota.toString())
        conexionEscritura.delete("NOTA", "id=?", consultaEliminar)
        conexionEscritura.close()
    }

    fun listarNotas(idAsignatura: Int): ArrayList<Nota> {
        val arregloNotas = ArrayList<Nota>()

        var resultadoConsultaListar: Cursor

        val conexionLectura = readableDatabase
        var scriptConsulta =
            """
                SELECT * FROM NOTA WHERE id_asignatura = ?
            """.trimIndent()

        resultadoConsultaListar = conexionLectura.rawQuery(scriptConsulta, arrayOf(idAsignatura.toString()))


        val existeUno = resultadoConsultaListar.moveToFirst()

        if (existeUno){
            do {
                val nota = Nota(
                    resultadoConsultaListar.getInt(0),
                    resultadoConsultaListar.getString(1),
                    resultadoConsultaListar.getString(2),
                )
                arregloNotas.add(nota)
            }while (resultadoConsultaListar.moveToNext())
        }
        resultadoConsultaListar.close()
        conexionLectura.close()
        return arregloNotas
    }
}