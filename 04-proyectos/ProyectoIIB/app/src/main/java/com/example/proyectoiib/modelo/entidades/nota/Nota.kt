package com.example.proyectoiib.modelo.entidades.nota

class Nota(
    var id: Int,
    var titulo: String,
    var descripcion: String
) {
    override fun toString(): String {
        return "Nota(id=$id, titulo='$titulo', descripcion='$descripcion')"
    }
}