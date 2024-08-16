package com.example.deber02_farmaciamedicamento

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(
    context: Context?
) : SQLiteOpenHelper(context, "FarmaciaMedicamentoDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaFarmacia = """
        CREATE TABLE Farmacia(
            idFarmacia INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreFarmacia VARCHAR(100),
            direccionFarmacia VARCHAR(200),
            numeroTotalMedicamentos INTEGER,
            abierta24Horas BOOLEAN
        );
    """.trimIndent()

        val crearTablaMedicamento = """
        CREATE TABLE Medicamento(
            idMedicamento INTEGER PRIMARY KEY AUTOINCREMENT,
            nombreMedicamento VARCHAR(100),
            esRecetado BOOLEAN,
            precioMedicamento REAL,
            idFarmacia INTEGER,
            FOREIGN KEY (idFarmacia) REFERENCES Farmacia(idFarmacia) ON DELETE CASCADE
        );
    """.trimIndent()

        db?.execSQL(crearTablaFarmacia)
        db?.execSQL(crearTablaMedicamento)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun obtenerTodasFarmacias(): ArrayList<Farmacia> {
        val lectureDB = readableDatabase
        val queryScript = """
        SELECT * FROM Farmacia
    """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            emptyArray()
        )
        val response = arrayListOf<Farmacia>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    Farmacia(
                        queryResult.getInt(0), // idFarmacia
                        queryResult.getString(1) ?: "", // nombreFarmacia
                        queryResult.getString(2) ?: "", // direccionFarmacia
                        queryResult.getInt(3), // numeroTotalMedicamentos
                        queryResult.getInt(4) == 1 // abierta24Horas
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun obtenerMedicamentosPorFarmacia(idFarmacia: Int): ArrayList<Medicamento> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM Medicamento WHERE idFarmacia=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(idFarmacia.toString())
        )
        val response = arrayListOf<Medicamento>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    Medicamento(
                        queryResult.getInt(0), // idMedicamento
                        queryResult.getString(1) ?: "", // nombreMedicamento
                        queryResult.getInt(2) == 1, // esRecetado
                        queryResult.getDouble(3), // precioMedicamento
                        queryResult.getInt(4) // idFarmacia
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun crearFarmacia(
        nombreFarmacia: String,
        direccionFarmacia: String,
        numeroTotalMedicamentos: Int,
        abierta24Horas: Boolean
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nombreFarmacia", nombreFarmacia)
        valuesToStore.put("direccionFarmacia", direccionFarmacia)
        valuesToStore.put("numeroTotalMedicamentos", numeroTotalMedicamentos)
        valuesToStore.put("abierta24Horas", if (abierta24Horas) 1 else 0)

        val storeResult = writeDB.insert(
            "Farmacia",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun actualizarFarmacia(
        idFarmacia: Int,
        nombreFarmacia: String,
        direccionFarmacia: String,
        numeroTotalMedicamentos: Int,
        abierta24Horas: Boolean
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nombreFarmacia", nombreFarmacia)
        valuesToUpdate.put("direccionFarmacia", direccionFarmacia)
        valuesToUpdate.put("numeroTotalMedicamentos", numeroTotalMedicamentos)
        valuesToUpdate.put("abierta24Horas", if (abierta24Horas) 1 else 0)

        val parametersUpdateQuery = arrayOf(idFarmacia.toString())
        val updateResult = writeDB.update(
            "Farmacia",
            valuesToUpdate,
            "idFarmacia=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun crearMedicamento(
        nombreMedicamento: String,
        esRecetado: Boolean,
        precioMedicamento: Double,
        idFarmacia: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nombreMedicamento", nombreMedicamento)
        valuesToStore.put("esRecetado", if (esRecetado) 1 else 0)
        valuesToStore.put("precioMedicamento", precioMedicamento)
        valuesToStore.put("idFarmacia", idFarmacia)

        val storeResult = writeDB.insert(
            "Medicamento",
            null,
            valuesToStore
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun actualizarMedicamento(
        idMedicamento: Int,
        nombreMedicamento: String,
        esRecetado: Boolean,
        precioMedicamento: Double,
        idFarmacia: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nombreMedicamento", nombreMedicamento)
        valuesToUpdate.put("esRecetado", if (esRecetado) 1 else 0)
        valuesToUpdate.put("precioMedicamento", precioMedicamento)
        valuesToUpdate.put("idFarmacia", idFarmacia)

        val parametersUpdateQuery = arrayOf(idMedicamento.toString())
        val updateResult = writeDB.update(
            "Medicamento",
            valuesToUpdate,
            "idMedicamento=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun borrarFarmacia(idFarmacia: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(idFarmacia.toString())
        val deleteResult = writeDB.delete(
            "Farmacia",
            "idFarmacia=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun borrarMedicamento(idMedicamento: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(idMedicamento.toString())
        val deleteResult = writeDB.delete(
            "Medicamento",
            "idMedicamento=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}
