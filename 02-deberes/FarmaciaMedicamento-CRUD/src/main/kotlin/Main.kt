import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Medicamento(
    val id: Int,
    var nombre: String,
    var fechaCaducidad: LocalDate,
    var esRecetado: Boolean,
    var precio: Double
)

data class Farmacia(
    val id: Int,
    var nombre: String,
    var direccion: String,
    var numeroTotalMedicamentos: Int,
    var abierta24Horas: Boolean,
    val medicamentos: MutableList<Medicamento>
)

fun crearMedicamento(): Medicamento {
    println("Ingrese el ID del Medicamento:")
    val id = readLine()!!.toInt()
    println("Ingrese el nombre del Medicamento:")
    val nombre = readLine()!!
    println("Ingrese la fecha de caducidad (yyyy-MM-dd):")
    val fechaCaducidad = LocalDate.parse(readLine(), DateTimeFormatter.ISO_DATE)
    println("¿Es recetado? (true/false):")
    val esRecetado = readLine()!!.toBoolean()
    println("Ingrese el precio del Medicamento:")
    val precio = readLine()!!.toDouble()
    return Medicamento(id, nombre, fechaCaducidad, esRecetado, precio)
}

fun leerMedicamento(medicamento: Medicamento) {
    println("ID: ${medicamento.id}")
    println("Nombre: ${medicamento.nombre}")
    println("Fecha de Caducidad: ${medicamento.fechaCaducidad}")
    println("Es Recetado: ${medicamento.esRecetado}")
    println("Precio: ${medicamento.precio}")
}

fun actualizarMedicamento(medicamento: Medicamento) {
    println("Ingrese el nuevo nombre del Medicamento:")
    medicamento.nombre = readLine()!!
    println("Ingrese la nueva fecha de caducidad (yyyy-MM-dd):")
    medicamento.fechaCaducidad = LocalDate.parse(readLine(), DateTimeFormatter.ISO_DATE)
    println("¿Es recetado? (true/false):")
    medicamento.esRecetado = readLine()!!.toBoolean()
    println("Ingrese el nuevo precio del Medicamento:")
    medicamento.precio = readLine()!!.toDouble()
}

fun eliminarMedicamento(medicamentos: MutableList<Medicamento>, id: Int) {
    val medicamento = medicamentos.find { it.id == id }
    if (medicamento != null) {
        medicamentos.remove(medicamento)
        println("Medicamento eliminado.")
    } else {
        println("Medicamento no encontrado.")
    }
}

fun crearFarmacia(): Farmacia {
    println("Ingrese el ID de la Farmacia:")
    val id = readLine()!!.toInt()
    println("Ingrese el nombre de la Farmacia:")
    val nombre = readLine()!!
    println("Ingrese la dirección de la Farmacia:")
    val direccion = readLine()!!
    println("Ingrese el número total de medicamentos:")
    val numeroTotalMedicamentos = readLine()!!.toInt()
    println("¿Está abierta 24 horas? (true/false):")
    val abierta24Horas = readLine()!!.toBoolean()
    return Farmacia(id, nombre, direccion, numeroTotalMedicamentos, abierta24Horas, mutableListOf())
}

fun leerFarmacia(farmacia: Farmacia) {
    println("ID: ${farmacia.id}")
    println("Nombre: ${farmacia.nombre}")
    println("Dirección: ${farmacia.direccion}")
    println("Número Total de Medicamentos: ${farmacia.numeroTotalMedicamentos}")
    println("Abierta 24 Horas: ${farmacia.abierta24Horas}")
    println("Medicamentos:")
    farmacia.medicamentos.forEach { leerMedicamento(it) }
}

fun actualizarFarmacia(farmacia: Farmacia) {
    println("Ingrese el nuevo nombre de la Farmacia:")
    farmacia.nombre = readLine()!!
    println("Ingrese la nueva dirección de la Farmacia:")
    farmacia.direccion = readLine()!!
    println("Ingrese el nuevo número total de medicamentos:")
    farmacia.numeroTotalMedicamentos = readLine()!!.toInt()
    println("¿Está abierta 24 horas? (true/false):")
    farmacia.abierta24Horas = readLine()!!.toBoolean()
}

fun eliminarFarmacia(farmacias: MutableList<Farmacia>, id: Int) {
    val farmacia = farmacias.find { it.id == id }
    if (farmacia != null) {
        farmacias.remove(farmacia)
        println("Farmacia eliminada.")
    } else {
        println("Farmacia no encontrada.")
    }
}

fun guardarMedicamentos(medicamentos: List<Medicamento>, archivo: String) {
    File(archivo).printWriter().use { out ->
        medicamentos.forEach { medicamento ->
            out.println("${medicamento.id},${medicamento.nombre},${medicamento.fechaCaducidad},${medicamento.esRecetado},${medicamento.precio}")
        }
    }
}

fun cargarMedicamentos(archivo: String): MutableList<Medicamento> {
    val medicamentos = mutableListOf<Medicamento>()
    File(archivo).forEachLine { line ->
        val datos = line.split(",")
        val medicamento = Medicamento(
            datos[0].toInt(),
            datos[1],
            LocalDate.parse(datos[2], DateTimeFormatter.ISO_DATE),
            datos[3].toBoolean(),
            datos[4].toDouble()
        )
        medicamentos.add(medicamento)
    }
    return medicamentos
}

fun guardarFarmacias(farmacias: List<Farmacia>, archivo: String) {
    File(archivo).printWriter().use { out ->
        farmacias.forEach { farmacia ->
            out.println("${farmacia.id},${farmacia.nombre},${farmacia.direccion},${farmacia.numeroTotalMedicamentos},${farmacia.abierta24Horas}")
            farmacia.medicamentos.forEach { medicamento ->
                out.println("    ${medicamento.id},${medicamento.nombre},${medicamento.fechaCaducidad},${medicamento.esRecetado},${medicamento.precio}")
            }
        }
    }
}

fun cargarFarmacias(archivo: String): MutableList<Farmacia> {
    val farmacias = mutableListOf<Farmacia>()
    val lines = File(archivo).readLines()
    var currentFarmacia: Farmacia? = null

    lines.forEach { line ->
        if (!line.startsWith("    ")) {
            val datos = line.split(",")
            currentFarmacia = Farmacia(
                datos[0].toInt(),
                datos[1],
                datos[2],
                datos[3].toInt(),
                datos[4].toBoolean(),
                mutableListOf()
            )
            farmacias.add(currentFarmacia!!)
        } else {
            val datos = line.trim().split(",")
            val medicamento = Medicamento(
                datos[0].toInt(),
                datos[1],
                LocalDate.parse(datos[2], DateTimeFormatter.ISO_DATE),
                datos[3].toBoolean(),
                datos[4].toDouble()
            )
            currentFarmacia?.medicamentos?.add(medicamento)
        }
    }

    return farmacias
}

fun main() {
    val farmacias = mutableListOf<Farmacia>()
    val archivoFarmacias = "farmacias.txt"

    // Cargar farmacias al inicio del programa
    val farmaciasCargadas = cargarFarmacias(archivoFarmacias)
    farmacias.addAll(farmaciasCargadas)
    println("Farmacias cargadas.")

    while (true) {
        println("Seleccione una opción:")
        println("1. Crear Farmacia")
        println("2. Leer Farmacia")
        println("3. Actualizar Farmacia")
        println("4. Eliminar Farmacia")
        println("5. Crear Medicamento")
        println("6. Leer Medicamento")
        println("7. Actualizar Medicamento")
        println("8. Eliminar Medicamento")
        println("9. Salir")
        when (readLine()!!.toInt()) {
            1 -> {
                val farmacia = crearFarmacia()
                farmacias.add(farmacia)
                println("Farmacia creada.")
            }
            2 -> {
                println("Ingrese el ID de la Farmacia:")
                val id = readLine()!!.toInt()
                val farmacia = farmacias.find { it.id == id }
                if (farmacia != null) {
                    leerFarmacia(farmacia)
                } else {
                    println("Farmacia no encontrada.")
                }
            }
            3 -> {
                println("Ingrese el ID de la Farmacia:")
                val id = readLine()!!.toInt()
                val farmacia = farmacias.find { it.id == id }
                if (farmacia != null) {
                    actualizarFarmacia(farmacia)
                    println("Farmacia actualizada.")
                } else {
                    println("Farmacia no encontrada.")
                }
            }
            4 -> {
                println("Ingrese el ID de la Farmacia:")
                val id = readLine()!!.toInt()
                eliminarFarmacia(farmacias, id)
            }
            5 -> {
                println("Ingrese el ID de la Farmacia donde desea agregar el Medicamento:")
                val idFarmacia = readLine()!!.toInt()
                val farmacia = farmacias.find { it.id == idFarmacia }
                if (farmacia != null) {
                    val medicamento = crearMedicamento()
                    farmacia.medicamentos.add(medicamento)
                    farmacia.numeroTotalMedicamentos++
                    println("Medicamento agregado.")
                } else {
                    println("Farmacia no encontrada.")
                }
            }
            6 -> {
                println("Ingrese el ID de la Farmacia:")
                val idFarmacia = readLine()!!.toInt()
                val farmacia = farmacias.find { it.id == idFarmacia }
                if (farmacia != null) {
                    println("Ingrese el ID del Medicamento:")
                    val idMedicamento = readLine()!!.toInt()
                    val medicamento = farmacia.medicamentos.find { it.id == idMedicamento }
                    if (medicamento != null) {
                        leerMedicamento(medicamento)
                    } else {
                        println("Medicamento no encontrado.")
                    }
                } else {
                    println("Farmacia no encontrada.")
                }
            }
            7 -> {
                println("Ingrese el ID de la Farmacia:")
                val idFarmacia = readLine()!!.toInt()
                val farmacia = farmacias.find { it.id == idFarmacia }
                if (farmacia != null) {
                    println("Ingrese el ID del Medicamento:")
                    val idMedicamento = readLine()!!.toInt()
                    val medicamento = farmacia.medicamentos.find { it.id == idMedicamento }
                    if (medicamento != null) {
                        actualizarMedicamento(medicamento)
                        println("Medicamento actualizado.")
                    } else {
                        println("Medicamento no encontrado.")
                    }
                } else {
                    println("Farmacia no encontrada.")
                }
            }
            8 -> {
                println("Ingrese el ID de la Farmacia:")
                val idFarmacia = readLine()!!.toInt()
                val farmacia = farmacias.find { it.id == idFarmacia }
                if (farmacia != null) {
                    println("Ingrese el ID del Medicamento:")
                    val idMedicamento = readLine()!!.toInt()
                    eliminarMedicamento(farmacia.medicamentos, idMedicamento)
                    farmacia.numeroTotalMedicamentos--
                } else {
                    println("Farmacia no encontrada.")
                }
            }
            9 -> {
                // Guardar farmacias al salir del programa
                guardarFarmacias(farmacias, archivoFarmacias)
                println("Farmacias guardadas.")
                return
            }
            else -> println("Opción inválida.")
        }
    }
}


