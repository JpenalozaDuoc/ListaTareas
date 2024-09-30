package com.example.listatareas.firebaseAuth

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AuthService {

    fun registrarUsuarioFirebase (
        nombre: String,
        email: String,
        contrasena: String,
        context: Context
    ){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contrasena).await()
                val userId = authResult.user?.uid
                val usuarioData = hashMapOf(
                    "nombre" to nombre,
                    "email" to email,
                    "uid" to userId  // Agregar UID del usuario al documento
                )
                FirebaseFirestore.getInstance().collection("usuarios").document(userId!!).set(usuarioData)
                    .addOnSuccessListener {

                        Toast.makeText(context, "Usuario Registrado con Exito",Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {e ->
                        Toast.makeText(context, "Error al Registrar en Firestore: ${e.message}", Toast.LENGTH_LONG).show()

                    }
            } catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error a Registrar: ${e.message}", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    fun iniciarSesionFirebase(
        email: String,
        contrasena: String,
        context: Context
    ){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, contrasena).await()
                val userId = authResult.user?.uid
                FirebaseFirestore.getInstance().collection("usuarios").document(userId!!).get()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Inicio de sesión exitoso.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




}