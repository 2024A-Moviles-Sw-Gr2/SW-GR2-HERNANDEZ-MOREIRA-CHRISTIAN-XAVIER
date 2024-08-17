package com.example.proyectoiib.modelo.entidades.actividad

class Actividad(
    var id: Int,
    var titulo: String,
    var fechaLimite: String,
    var descripcion: String
) {
    override fun toString(): String {
        return "Actividad(id=$id, titulo='$titulo', fechaLimite='$fechaLimite', descripcion='$descripcion')"
    }
}