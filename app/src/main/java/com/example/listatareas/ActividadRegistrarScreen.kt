package com.example.listatareas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson

@SuppressLint("CommitPrefEdits")
@Composable

fun RegistrarActividadScreen (viewModel: ActividadesViewModel){

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("mis_datos", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(text = "Registro de Nuevas Actividades",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)
        Spacer(modifier = Modifier.height(12.dp))

        Image(painter = painterResource(id = R.drawable.pig_thor), contentDescription = "Imagen Login",
            modifier = Modifier.size(120.dp))

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(10.dp))
        //Definición del Campo Titulo
        OutlinedTextField(value = nombre, onValueChange = {
            nombre = it
        }, label = { Text(text = "Nombre")} )


        Spacer(modifier = Modifier.height(10.dp))
        //Definición del Campo Email
        OutlinedTextField(value = descripcion, onValueChange = {
            descripcion = it
        }, label = { Text(text = "Descripcion") })

        Spacer(modifier = Modifier.height(25.dp))
        //Crear el boton de registrar nuevas Actividades

        Button(
            onClick = {

                if (nombre.isBlank() || descripcion.isBlank()) {

                    //Si uno de los 2 campos se encuentra vació, retornará el mensaje de la siguiente línea
                    Toast.makeText(context, "Por favor, complete los campos de las Actividades", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    // Define una expresión regular para buscar caracteres maliciosos
                    val caracteresMaliciosos = "[<>&\"';]".toRegex()

                    // Compara el contenido ingresado en cada campo para confirmar si tiene caracteres regulares definidos en la variable caracteres Maliciosos.
                    if (caracteresMaliciosos.containsMatchIn(nombre) ||
                        caracteresMaliciosos.containsMatchIn(descripcion)) {
                        //en Caso de encontrar caracteres especiales, se levanta alaerta
                        Toast.makeText(context, "Alerta: Se detectaron caracteres potencialmente maliciosos.", Toast.LENGTH_LONG).show()
                    } else {
                        // Crea un objeto Actividaded con los datos ingresados

                        // Crear un arreglo asociativo (Map)
                        val datosUsuario: Map<String, Any> = mapOf(
                            "nombre" to nombre,
                            "descripcion" to descripcion
                        )

                        // Convertir el Map a JSON
                        val gson = Gson()
                        val jsonString = gson.toJson(datosUsuario)

                        // Guardar el JSON en SharedPreferences
                        editor.putString("nombre_"+nombre, jsonString)
                        editor.apply()

                        // Limpia los campos de texto después de guardar
                        nombre = ""
                        descripcion = ""
                        Toast.makeText(context, "Registro exitoso.", Toast.LENGTH_LONG).show()


                    }

                }

            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0C6E7A),
                contentColor = Color.White
            )
        ) {
            Text(text = "Registrar Actividad")

        }

        //Aqui debe ir el codigo para separar del boton
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Regresar",
            modifier = Modifier.clickable {
                val navigate = Intent(context, HomeActivity::class.java)
                context.startActivity(navigate)
            },
            fontWeight = FontWeight.SemiBold
        )

    }


}