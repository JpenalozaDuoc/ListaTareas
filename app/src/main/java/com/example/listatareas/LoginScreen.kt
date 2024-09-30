package com.example.listatareas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


@SuppressLint("SuspiciousIdentation")
@Composable
fun LoginScreen(viewModel: ViewModel){



    val gradientColors = listOf(
        Color(0xFFEC6969),
        Color(0xFF7AD3DE)
    )

    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Crear una instancia de FirebaseAuth
    val firebaseAuth = FirebaseAuth.getInstance()

    //Preferencias compartidas
    val sharedPreferences = context.getSharedPreferences("mis_datos", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    //Se crea una instancia de Gson
    val gson = Gson()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Bienvenido",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)
        Spacer(modifier = Modifier.height(12.dp))

        Image(painter = painterResource(id = R.drawable.pig_iron), contentDescription = "Imagen Login",
            modifier = Modifier.size(180.dp))

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = { Text(text="Email") })

        Spacer(modifier = Modifier.height(12.dp))

       OutlinedTextField(value = contrasena, onValueChange = {
            contrasena = it
        }, label = { Text(text="Contraseña")
        }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {

            if (email.isBlank()) {
                Toast.makeText(context, "Campo vacio, Ingrese un email", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Email incorrecto, Ingrese un Email válido", Toast.LENGTH_SHORT).show()
            } else {
                /*
                // Leer el JSON de SharedPreferences
                val jsonStringRecuperado = sharedPreferences.getString("email_"+email, null)

                // Convertir el JSON de nuevo a un Map
                val tipoMapa = object : TypeToken<Map<String, Any>>() {}.type
                val usuarioEncontrado: Map<String, Any> = gson.fromJson(jsonStringRecuperado, tipoMapa)

                //Si encuentra al Usuario y la contraseña es correcta al igual que el usuario
                if (usuarioEncontrado != null) {
                    Toast.makeText(context, "Usuario correcto, puede ingresar", Toast.LENGTH_SHORT).show()
                    val navigate = Intent(context, HomeActivity::class.java)
                    context.startActivity(navigate)
                } else {
                    // Credenciales inválidas
                    Toast.makeText(context, "Contraseña Incorrecta, reingrese por favor", Toast.LENGTH_SHORT).show()
                }*/
                //Llamar al Inicio de Sesionde Firebas
                iniciarSesionFirebase(email, contrasena,context,firebaseAuth)
            }

        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF2E383A)
            )
        ) {
            Text(text = "Iniciar Sesión")
        }
        Spacer(modifier = Modifier.height(25.dp))

        //olvide mi contrseña y registrarse
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =  Arrangement.SpaceEvenly

        ) {

            Text(text = "¿Olvidaste tu contraseña?",
                modifier = Modifier.clickable {
                    //val navigate = Intent(context, RetrieveScreen::class.java)
                    val navigate = Intent(context, RetrieveScreen::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold
            )

            Text(text = "Registrarse",
                modifier = Modifier.clickable {
                    val navigate = Intent(context, RegistroActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold)
        }


    }
}

fun iniciarSesionFirebase(email: String, contrasena: String, context: Context, firebaseAuth: FirebaseAuth) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Intentar iniciar sesión con FirebaseAuth
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, contrasena).await()

            withContext(Dispatchers.Main) {
                // Si el inicio de sesión fue exitoso
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()

                // Navegar a la siguiente actividad
                val navigate = Intent(context, HomeActivity::class.java)
                navigate.putExtra("nombreUsuario", email as String)
                context.startActivity(navigate)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                // Si ocurre un error, mostrar el mensaje de error
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}