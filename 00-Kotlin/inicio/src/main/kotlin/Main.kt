import java.util.*

fun main() {
    println("Hola mundo")
    // Inmutables (No se puede RE ASIGNAR "=")
    val inmutable: String = "Christian"
    // Usar ; es redundante, mala práctica
    // inmutable = "Xavier" //ERROR!

    // Mutables
    var mutable: String = "Xavier"
    mutable = "Christian" //OK

    // VAL > VAR

    // Duck Typing
    var ejemploVariable = " Christian Hernández "
    // No es necesario especificar el tipo de variable cuando se instancia
    // porque el compilador lo interpreta

    val edadEjemplo: Int = 12
    ejemploVariable.trim() //OK
    //ejemploVariable = edadEjemplo //ERROR! tipo incorrecto
    val nombre: String = "Christian"
    val sueldo: Double = 1.2
    val estadoCivil: Char = "S"
    val mayorEdad: Boolean = true
    // Clases de Java
    val fechaNacimiento: Date = Date()

}