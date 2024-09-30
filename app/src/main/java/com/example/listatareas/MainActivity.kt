package com.example.listatareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.listatareas.ui.theme.ListaTareasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelSingleton.usuariosViewModel
        setContent {

            LoginScreen(viewModel = viewModel)
            }
        }
    }


@Preview
@Composable
fun GreetingPreview() {
    val viewModel = ViewModelSingleton.usuariosViewModel
    LoginScreen(viewModel)
}