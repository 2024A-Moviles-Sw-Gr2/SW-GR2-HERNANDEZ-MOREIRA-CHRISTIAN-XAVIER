package com.example.proyectoiib.modelo.entidades.asignatura

class Asignatura(
    var id:Int,
    var nombre: String,
    var profesor: String,
    var aula: String
) {
    override fun toString(): String {
        return "Actividad(id=$id, nombre='$nombre', profesor='$profesor', aula='$aula')"
    }
}