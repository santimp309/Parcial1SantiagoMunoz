package com.example.farmaciaapp.model

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
