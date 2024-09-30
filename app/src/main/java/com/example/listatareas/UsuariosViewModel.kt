package com.example.listatareas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class UsuariosViewModel: ViewModel() {
    var listaUsuarios = mutableStateListOf<Usuarios>()
}