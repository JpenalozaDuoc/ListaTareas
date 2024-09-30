package com.example.listatareas.Operaciones

open class TareasCompletas (_nombre:String, _descrpcion:String) {

    var nombre = _nombre
    var descripcion = _descrpcion

    fun obtener_lista_tareas_completas(): Set<String> {
        return setOf("Limpiar la Casa", "Preparar Informe Sumativa 3", "DevOps Informe")
    }
}