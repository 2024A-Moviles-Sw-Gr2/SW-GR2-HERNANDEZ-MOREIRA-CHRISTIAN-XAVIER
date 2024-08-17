package com.example.proyectoiib.modelo.entidades.asignatura

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.proyectoiib.modelo.baseDatos.SQLiteHelper

class TablaAsignatura(contexto: Context?): SQLiteHelper(contexto) {
    fun agregarAsignatura(
        nombre: String,
        profesor: String,
        aula: String
    ){
        val conexionEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("nombre", nombre)
        valoresGuardar.put("profesor", profesor)
        valoresGuardar.put("aula", aula)
        conexionEscritura.insert("ASIGNATURA", null, valoresGuardar)
    }

    fun eliminarAsignatura(idAsignatura: Int){
        val conexionEscritura = writableDatabase
        val consultaEliminar = arrayOf(idAsignatura.toString())
        conexionEscritura.delete("ASIGNATURA", "id=?", consultaEliminar)
        conexionEscritura.close()
    }

    fun listarAsignatura(): ArrayList<Asignatura> {
        val arregloAsignaturas = ArrayList<Asignatura>()

        var resultadoConsultaListar: Cursor

        val conexionLectura = readableDatabase
        var scriptConsulta =
            """
                SELECT * FROM ASIGNATURA
            """.trimIndent()

        resultadoConsultaListar = conexionLectura.rawQuery(scriptConsulta,null)


        val existeUno = resultadoConsultaListar.moveToFirst()

        if (existeUno){
            do {
                val asignatura = Asignatura(
                    resultadoConsultaListar.getInt(0),
                    resultadoConsultaListar.getString(1),
                    resultadoConsultaListar.getString(2),
                    resultadoConsultaListar.getString(3)
                )
                arregloAsignaturas.add(asignatura)
            }while (resultadoConsultaListar.moveToNext())
        }
        resultadoConsultaListar.close()
        conexionLectura.close()
        return arregloAsignaturas
    }



}