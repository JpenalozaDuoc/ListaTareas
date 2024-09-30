package com.example.listatareas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ActividadesViewModel : ViewModel() {
    var listaActividades = mutableStateListOf<Actividades>()
}