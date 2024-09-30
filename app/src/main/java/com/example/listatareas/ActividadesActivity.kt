package com.example.listatareas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ActividadesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelSingleton.actividadesViewModel
        setContent{
            ListarActividades(viewModel)
        }
    }

    //Aca va el codigo de la aplicación para listar las actividades
    @Composable
    fun ListarActividades(viewModel: ActividadesViewModel){

        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("mis_datos", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ){

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                Image(painter = painterResource(id = R.drawable.pig_thor),
                    contentDescription = "Imagen Icono",
                    modifier = Modifier.size(120.dp)            )
            }
            Text(text = "Listado de Actividades Registradas:",
                fontSize = 12.sp,
                fontWeight= FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(20.dp, 20.dp))

            // Crear un TypeToken para Map<String, Any>
            val tipoMapa = object : TypeToken<Map<String, Any>>() {}.type

            // Obtener todos los datos de SharedPreferences
            val allEntries = sharedPreferences.all
            val nombres = allEntries
                .filter { it.key.startsWith("nombre_") }
                .map { (_, value) ->
                    val jsonString = value as? String
                    jsonString?.let {
                        gson.fromJson(jsonString, tipoMapa) as? Map<String, Any>
                    }
                }
                .filterNotNull()

            val descripciones = allEntries
                .filter { it.key.startsWith("descripcion_") }
                .map { (_, value) ->
                    val jsonString = value as? String
                    jsonString?.let {
                        gson.fromJson(jsonString, tipoMapa) as? Map<String, Any>
                    }
                }
                .filterNotNull()




            LazyColumn {
                //items (viewModel.listaActividades)
                items(descripciones)
                {
                        actividad -> Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
                    colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
                )  {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Image(painter = painterResource(R.drawable.actividades), contentDescription = "Login Imagen",
                            modifier = Modifier.size(50.dp))

                        Text(text = actividad["nombre"].toString(),
                            fontSize = 16.sp,
                            fontWeight= FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp))

                        Text(text = actividad["descripcion"].toString(),
                            fontSize = 16.sp,
                            fontWeight= FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp))

                    }

                }
                } // Color de fondo
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text(text = "Volver",
                    modifier = Modifier.clickable {
                        val navigate = Intent(context, HomeActivity::class.java)
                        context.startActivity(navigate)
                    },
                    fontWeight = FontWeight.SemiBold
                )


            }

        }
        // boton Volver


    }
    //Aqui debo poner el código para devolverme o intentar mostrar otro contenido

}