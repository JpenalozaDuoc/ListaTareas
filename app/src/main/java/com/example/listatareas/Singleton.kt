package com.example.listatareas

// Singletons.kt
object ViewModelSingleton {
    val usuariosViewModel: UsuariosViewModel by lazy {
        UsuariosViewModel()
    }

    val actividadesViewModel : ActividadesViewModel by lazy{
        ActividadesViewModel()
    }

}
