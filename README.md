#Prompts utilizados
1. Necesito hacer con el caso de estudio de una farmacia, realizar un modelo de clases, implementación de las clases en el proyecto, diseño de pantallas, aplicación con dos pantallas que liste al menos dos de los objetos implementados. Para el diseño de clases pensé en hacer con este diagrama. Ayudame a revisar si está bien y cuál seria el siguiente paso a realizar en el proyecto usando Android Studio.  
(se adjunta la imagen del diagrama de clases)
2. I need to create an app about pharmacy information. Please, give me a version to run just with these class

   data class Laboratorio(
    val id: String,
    val nombre: String,
    val paisOrigen: String
) {
    fun obtenerInformacion(): String {
        return "$nombre Pais $paisOrigen"
    }
}
   data class Medicamento(
    val id: String,
    val nombre: String,
    val precio: Double,
    var stock: Int,
    val requiereReceta: Boolean,
    val laboratorio: Laboratorio
) {
    fun hayStockDisponible(): Boolean {
        return stock > 0
    }
}

class InventarioFarmacia {
    private val medicamentos = mutableListOf<Medicamento>()
    private val laboratorios = mutableListOf<Laboratorio>()

    fun agregarLaboratorio(laboratorio: Laboratorio) {
        laboratorios.add(laboratorio)
    }

    fun agregarMedicamento(medicamento: Medicamento) {
        medicamentos.add(medicamento)
    }

    fun obtenerTodosLosMedicamentos(): List<Medicamento> {
        return medicamentos.toList()
    }

    fun obtenerTodosLosLaboratorios(): List<Laboratorio> {
        return laboratorios.toList()
    }
}

