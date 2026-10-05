classDiagram
    class CategoriaFarmacia {
        <<enumeration>>
        ANALGESICOS
        ANTIBIOTICOS
        VITAMINAS
        CUIDADO_PERSONAL
        PRIMEROS_AUXILIOS
    }

    class Laboratorio {
        - id : String
        - nombre : String
        - paisOrigen : String
        + obtenerInformacion() String
    }

    class Medicamento {
        - id : String
        - nombre : String
        - precio : Double
        - stock : Int
        - requiereReceta : Boolean
        + actualizarStock(cantidad: Int)
        + hayStockDisponible() Boolean
    }

    class InventarioFarmacia {
        - medicamentos : List~Medicamento~
        - laboratorios : List~Laboratorio~
        + agregarLaboratorio(laboratorio: Laboratorio)
        + agregarMedicamento(medicamento: Medicamento)
        + obtenerTodosLosMedicamentos() List~Medicamento~
        + obtenerTodosLosLaboratorios() List~Laboratorio~
    }

    Medicamento --> Laboratorio : tiene un
    Medicamento --> CategoriaFarmacia : categorizado como
    InventarioFarmacia o-- Medicamento : contiene
    InventarioFarmacia o-- Laboratorio : contiene
