package com.example.farmaciaapp.model

data class Laboratorio(
    val id: String,
    val nombre: String,
    val paisOrigen: String
) {
    fun obtenerInformacion(): String {
        return "$nombre Pais $paisOrigen"
    }
}
