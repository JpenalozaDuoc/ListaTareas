package com.example.listatareas.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuario")
    fun obtenerUsuarios(): List<Usuario>

    @Insert
    fun addUser(usuario: Usuario):Long

    @Query("SELECT * FROM usuario WHERE email = :correo AND contrasena = :contrasena LIMIT 1")
    fun iniciarSesion(correo: String, contrasena: String): Usuario?
}