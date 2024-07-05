package com.example.a2024aswgr2cxhm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ESqliteHelperEntrenador (
    contexto: Context // This
): SQLiteOpenHelper (
    contexto,
    "moviles",
    null,
    1
        ) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(
        p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Nombre de la tabla
            null,
                valoresAGuardar     // Valores
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }
}