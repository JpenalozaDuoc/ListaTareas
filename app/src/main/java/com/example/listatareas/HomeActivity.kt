package com.example.listatareas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listatareas.Operaciones.TareasCompletas
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelSingleton.usuariosViewModel
        setContent{
            ListaUsuarios(viewModel)
        }
    }

    @Composable
    fun ListaUsuarios(viewModel: UsuariosViewModel){

        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("mis_datos", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()

        var textoInfo: String=""
        val tareaCompleta = TareasCompletas("Imprimir informe","Realizar la impresion del informe")


            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ){

                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {


                    Image(painter = painterResource(id = R.drawable.pig_hammer),
                        contentDescription = "Imagen Icono",
                        modifier = Modifier.size(100.dp)            )
                }
                Text(text = "Listado de Personas Registradas:",
                    fontSize = 12.sp,
                    fontWeight= FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(20.dp, 20.dp))

                // Crear un TypeToken para Map<String, Any>
                val tipoMapa = object : TypeToken<Map<String, Any>>() {}.type

                // Obtener todos los datos de SharedPreferences
                val allEntries = sharedPreferences.all
                val usuarios = allEntries
                    .filter { it.key.startsWith("nombre_") }
                    .map { (_, value) ->
                        val jsonString = value as? String
                        jsonString?.let {
                            gson.fromJson(jsonString, tipoMapa) as? Map<String, Any>
                        }
                    }
                    .filterNotNull()

                val emails = allEntries
                    .filter { it.key.startsWith("email_") }
                    .map { (_, value) ->
                        val jsonString = value as? String
                        jsonString?.let {
                            gson.fromJson(jsonString, tipoMapa) as? Map<String, Any>
                        }
                    }
                    .filterNotNull()




                LazyColumn {
                    //items (viewModel.listaUsuarios)
                    items(emails)
                    {
                            persona -> Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp, 5.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
                        colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
                    )  {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Image(painter = painterResource(R.drawable.avatar), contentDescription = "Login Imagen",
                                modifier = Modifier.size(40.dp))

                            Text(text = persona["nombre"].toString(),
                                fontSize = 12.sp,
                                fontWeight= FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp))

                            Text(text = persona["email"].toString(),
                                fontSize = 12.sp,
                                fontWeight= FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp))

                            }

                        }
                    } // Color de fondo
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(text = "Salir",
                        modifier = Modifier.clickable {
                            val navigate = Intent(context, LoginActivity::class.java)
                            context.startActivity(navigate)
                        },
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Registrar Actividades",
                        modifier = Modifier.clickable {
                            val navigate = Intent(context, ActividadRegistrarActivity::class.java)
                            context.startActivity(navigate)
                        },
                        fontWeight = FontWeight.SemiBold
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "Lista Actividades",
                        modifier = Modifier.clickable {
                            val navigate = Intent(context, ActividadesActivity::class.java)
                            context.startActivity(navigate)
                        },
                        fontWeight = FontWeight.SemiBold
                    )

                }

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(12.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                ) {
//                    Text(text = "Mostrar Actividaes Completadas",
//                        modifier = Modifier.clickable {
//                            //val navigate = Intent(context, ActividadesActivity::class.java)
//                            //context.startActivity(navigate)
//                            textoInfo = tareaCompleta.obtener_lista_tareas_completas().toString()
//                            Toast.makeText(context, textoInfo, Toast.LENGTH_LONG).show()
//                        },
//                        fontWeight = FontWeight.SemiBold
//                    )
//
//                }


            }
            // boton Volver


        }
        //Aqui debo poner el código para devolverme o intentar mostrar otro contenido


}
