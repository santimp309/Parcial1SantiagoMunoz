package com.example.farmaciaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.farmaciaapp.model.*
import com.example.farmaciaapp.ui.theme.FarmaciaAppTheme
import java.util.Locale

enum class Pantalla(val titulo: String) {
    MEDICAMENTOS("MEDICAMENTOS"),
    LABORATORIOS("LABORATORIOS")
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FarmaciaAppTheme {
                val inventario = remember { getMockInventario() }
                var pantallaActual by remember { mutableStateOf(Pantalla.MEDICAMENTOS) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("SISTEMA FARMACIA") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = {  }, // Icono removido
                                label = { 
                                    Text(
                                        text = Pantalla.MEDICAMENTOS.titulo,
                                        fontWeight = if (pantallaActual == Pantalla.MEDICAMENTOS) FontWeight.Bold else FontWeight.Normal
                                    ) 
                                },
                                selected = pantallaActual == Pantalla.MEDICAMENTOS,
                                onClick = { pantallaActual = Pantalla.MEDICAMENTOS },
                                // Ocultar el ícono para forzar que solo se vea el texto centrado
                                alwaysShowLabel = true
                            )
                            NavigationBarItem(
                                icon = {  }, // Icono removido
                                label = { 
                                    Text(
                                        text = Pantalla.LABORATORIOS.titulo,
                                        fontWeight = if (pantallaActual == Pantalla.LABORATORIOS) FontWeight.Bold else FontWeight.Normal
                                    ) 
                                },
                                selected = pantallaActual == Pantalla.LABORATORIOS,
                                onClick = { pantallaActual = Pantalla.LABORATORIOS },
                                alwaysShowLabel = true
                            )
                        }
                    }
                ) { innerPadding ->
                    when (pantallaActual) {
                        Pantalla.MEDICAMENTOS -> MedicamentosScreen(
                            medicamentos = inventario.obtenerTodosLosMedicamentos(),
                            modifier = Modifier.padding(innerPadding)
                        )
                        Pantalla.LABORATORIOS -> LaboratoriosScreen(
                            laboratorios = inventario.obtenerTodosLosLaboratorios(),
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

// --- INTERFAZ 1: PANTALLA DE MEDICAMENTOS ---
@Composable
fun MedicamentosScreen(medicamentos: List<Medicamento>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(medicamentos) { medicamento ->
            MedicamentoCard(medicamento = medicamento)
        }
    }
}

@Composable
fun MedicamentoCard(medicamento: Medicamento) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = medicamento.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Laboratorio: ${medicamento.laboratorio.nombre}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bs. ${String.format(Locale.US, "%.2f", medicamento.precio)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                // Etiqueta dinámica: Rx Receta vs Venta Libre
                if (medicamento.requiereReceta) {
                    Text(
                        text = "[ Rx Receta ]",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "[ Venta Libre]",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// --- INTERFAZ 2: PANTALLA DE LABORATORIOS ---
@Composable
fun LaboratoriosScreen(laboratorios: List<Laboratorio>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
    ) {
        items(laboratorios) { lab ->
            ListItem(
                headlineContent = {
                    Text(
                        text = lab.nombre,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                supportingContent = {
                    Text(
                        text = "Origen: ${lab.paisOrigen}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = "Icono Laboratorio",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
            HorizontalDivider() // Divisor entre elementos
        }
    }
}

// --- DATOS DE PRUEBA Y PREVIEWS ---
fun getMockInventario(): InventarioFarmacia {
    val inventario = InventarioFarmacia()

    val lab1 = Laboratorio("1", "Droguería INTI", "Bolivia")
    val lab2 = Laboratorio("2", "Bagó", "Argentina")
    val lab3 = Laboratorio("3", "Bayer", "Alemania")

    inventario.agregarLaboratorio(lab1)
    inventario.agregarLaboratorio(lab2)
    inventario.agregarLaboratorio(lab3)

    inventario.agregarMedicamento(Medicamento("1", "Paracetamol 500mg", 5.50, 100, false, lab1))
    inventario.agregarMedicamento(Medicamento("2", "Amoxicilina 1g", 25.00, 50, true, lab2))
    inventario.agregarMedicamento(Medicamento("3", "Mentisan Ungüento", 15.00, 200, false, lab1))

    return inventario
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    FarmaciaAppTheme {
        MedicamentosScreen(medicamentos = getMockInventario().obtenerTodosLosMedicamentos())
    }
}
