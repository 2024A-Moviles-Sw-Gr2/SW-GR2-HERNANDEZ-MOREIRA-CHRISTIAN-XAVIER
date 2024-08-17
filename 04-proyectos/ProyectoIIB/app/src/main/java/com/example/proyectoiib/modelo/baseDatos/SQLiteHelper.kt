package com.example.proyectoiib.modelo.baseDatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Clase encargada de la creación de la BD
open class SQLiteHelper(contexto: Context?):SQLiteOpenHelper(contexto, "proyectoiib", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaAsignatura =
            """
                CREATE TABLE ASIGNATURA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(100),
                    profesor VARCHAR(100),
                    aula VARCHAR(100)
                )
            """.trimIndent()
        db?.execSQL(scriptCrearTablaAsignatura)

        val scriptCrearTablaActividad =
            """
                CREATE TABLE ACTIVIDAD(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo VARCHAR(100),
                    fechaLimite VARCHAR(100),
                    descripcion VARCHAR(100),
                    id_asignatura INTEGER,
                    FOREIGN KEY (id_asignatura) REFERENCES ASIGNATURA(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptCrearTablaActividad)

        val scriptCrearTablaNota =
            """
                CREATE TABLE NOTA(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo VARCHAR(100),
                    descripcion VARCHAR(100),
                    id_asignatura INTEGER,
                    FOREIGN KEY (id_asignatura) REFERENCES ASIGNATURA(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptCrearTablaNota)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys = ON")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}