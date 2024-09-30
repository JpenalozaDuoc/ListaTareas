package com.example.listatareas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


class RetrieveScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperarContrasena()
        }
    }

    @Composable
    fun  RecuperarContrasena(){

        var email by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current

        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Image(painter = painterResource(id=R.drawable.olvidocontrasena), contentDescription = "Imagen Olvido",
                modifier = Modifier.size(180.dp))

            Spacer(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(value = email, onValueChange = {
                email = it
            }, label = { Text(text = "Correo Electrónico")})

            Spacer(modifier = Modifier.height(5.dp))

            //Botón de Recuperar Contraseña
            Button(
                onClick = {
                    if (email.isBlank()) {
                        //Valida si el campo Email esta vacio
                        Toast.makeText(context, "Por favor, ingrese un Correo Electrónico", Toast.LENGTH_SHORT).show()
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        //Valida el formato del correo
                        Toast.makeText(context, "Por favor, ingrese un Correo Electrónico válido", Toast.LENGTH_SHORT).show()
                    } else {
                        // ... tu código para manejar el email válido ...
                        Toast.makeText(context, "Por favor,revise en su correo las instrucciones para recuperar su contraeña", Toast.LENGTH_SHORT).show()
                        val navigate =Intent(context, LoginActivity::class.java)
                        context.startActivity(navigate)

                    }

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(280.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF092A62),
                    contentColor = Color.White
                )

            ) {
                Text(text = "Recuperar Contraseña")
            }

            //Volver en caso de que no redireccione
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =  Arrangement.SpaceEvenly

            ) {

                Text(text = "Volver",
                    modifier = Modifier.clickable {
                        val navigate = Intent(context, MainActivity::class.java)
                        context.startActivity(navigate)
                    },
                    fontWeight = FontWeight.SemiBold
                )

            }

        }

    }

    @Preview
    @Composable
    fun VistaPreviaRecuperar()
    {
        RecuperarContrasena()
    }

}


