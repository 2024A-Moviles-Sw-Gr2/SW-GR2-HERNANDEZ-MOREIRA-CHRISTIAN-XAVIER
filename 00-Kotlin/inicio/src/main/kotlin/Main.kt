import java.util.*
import kotlin.collections.ArrayList

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
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true
    // Clases de Java
    val fechaNacimiento: Date = Date()

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //if else chiquito


    calcularSueldo(10.0)
    calcularSueldo(10.00,15.00, 20.00)
    //Named Parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    // Uso de clases
    val sumaUno = Suma(1,1) // new Suma(1,1) en KOTLIN no hay "new"
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglos
    // Estáticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    // Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

    println(arregloDinamico)
    // Agrega el elemento en el índice deseado
    arregloDinamico.add(10, 11)
    // Agrega el elemento al final del arreglo
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH => Unit (devuelve void)
    // Iterar un arreglo
    println("\n ** Primera forma **")
    val respuestaForEach: Unit = arregloDinamico
        .forEach {valorActual: Int ->
            println("Valor actual: ${valorActual}");
        }

    // "it" (en ignlés "eso") significa el elemento iterado
    println("\n ** Segunda forma **")
    arregloDinamico.forEach{ println("Valor actual (it): ${it}") }

    // MAP -> MUTA(Modifica, cambia) el arreglo
    // 1) Enviamos el nuevo valor de la iteración
    // 2) Nos devuelve un NUEVO ARREGLO con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico
        .map {valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println("\n" + respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it +15 }

    println("\n" + respuestaMapDos)
}

// void -> Unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}") //Template String
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null //Opcional (nullable)
    //Variable? -> "?" Es Nullable (o sea que puede en algún momento ser nulo)
): Double {
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros ( //Constructor primario
        // Caso 1) Parametro nomral
        // uno:Int, (parametro (sin modificar acceso))

        // Caso 2) Parametro y propiedad (atributo)(private)
        // private var uno:Int (propiedad "instancia.uno")

        protected val numeroUno: Int, //instancia numeroIno
        protected val numeroDos: Int, //instancia numeroDos
        //parametroInutil: String, //Parámetro
        ){
    init { //bloque constructor primario (OPCIONAL)
        this.numeroUno
        this.numeroDos
        //this.parametroInutil //ERROR no existe
        println("Inicializando")
    }
}

class Suma( //Constructor primario
    unoParametro: Int, //Parámetro
    dosParametro: Int, //Parámetro
): Numeros ( // Clase papá, Numeros (extendiendo)
    unoParametro,
    dosParametro
        ){
    public val soyPublicoExplicito: String = "Explícito" //Publicos
    val soyPublicoImplicito: String= "Implícito" //Publicas (propiedad, metodos)
    init{ //Bloque Código Constructor primario
        // this.unoParametro //ERROR no existe
        this.numeroUno
        this.numeroDos
        numeroUno //this. OPCIONAL (propiedades, métodos)
        numeroDos //this. OPCIONAL (propiedades, métodos)
        this.soyPublicoExplicito
        soyPublicoImplicito //this. OPCIONAL (propiedades, métodos)
    }

    constructor( //Constructor secundario
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos,
    )

    constructor( //Tercer constructor
        uno: Int,
        dos: Int?
    ):this(
        uno,
        if(dos == null) 0 else dos,
    )

    constructor( //Cuarto constructor
        uno: Int?,
        dos: Int?
    ):this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos,
    )

    // public fun sumar()Int{ (Modificar "public" es OPCIONAL)
    fun sumar():Int{
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }

    companion object{ // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
        }
}