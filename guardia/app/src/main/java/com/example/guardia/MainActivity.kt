package com.example.guardia

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity // Import cambiado
import androidx.activity.compose.setContent // Import para setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button // Import para Button de Material 3
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text // Import para Text de Material 3
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// Asegúrate de tener un tema de Compose, por ejemplo, en ui.theme.YourAppTheme
import com.example.guardia.ui.theme.GuardiaTheme // Ajusta esto a tu tema real

class MainActivity : ComponentActivity() { // Cambiado a ComponentActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // Usa setContent para Compose
            GuardiaTheme { // Aplica tu tema de Compose
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(
                        onLoginClick = {
                            startActivity(Intent(this, LoginActivity::class.java))
                        },
                        onRegisterClick = {
                            // Asegúrate de que RegisterActivity exista y esté declarada
                            startActivity(Intent(this, RegisterActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onLoginClick) {
            Text("Ir a Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegisterClick) {
            Text("Ir a Registro")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GuardiaTheme {
        MainScreen({}, {})
    }
}

