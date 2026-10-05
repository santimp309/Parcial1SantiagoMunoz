package com.example.farmaciaapp.model

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
