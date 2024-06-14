package com.example.a2024aswgr2cxhm

class BEntrenador (
    var id:Int,
    var nombre:String,
    var descripcion:String?
        ){

    override fun toString(): String {
        return "$nombre ${descripcion}"
    }
}