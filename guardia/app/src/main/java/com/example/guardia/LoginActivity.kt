package com.example.guardia

import android.content.Intent // Necesario si vas a navegar a otra Activity después del login
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// Asegúrate de tener un tema de Compose, por ejemplo, en ui.theme.YourAppTheme
import com.example.guardia.ui.theme.GuardiaTheme // Ajusta esto a tu tema real

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuardiaTheme { // Aplica tu tema de Compose
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // Para componentes de Material 3 como OutlinedTextField
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() }, // trim() para quitar espacios al inicio/final
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Correo y contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show()
                    return@Button
                }

                isLoading = true
                // --- Lógica de Autenticación (Simulada) ---
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    isLoading = false
                    if (email == "test@example.com" && password == "password") {
                        Toast.makeText(context, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                        // Aquí navegarías a la siguiente pantalla principal de tu app después del login
                        // Ejemplo: Navegar a una HomeActivity (asegúrate que HomeActivity exista)
                        // val intent = Intent(context, HomeActivity::class.java)
                        // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Para limpiar el stack
                        // context.startActivity(intent)
                        // (context as? ComponentActivity)?.finish() // Cierra LoginActivity
                    } else {
                        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                    }
                }, 1500) // Retraso reducido para pruebas más rápidas
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Ingresar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = {
            // Navegar a RegisterActivity
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }) {
            Text("¿No tienes cuenta? Regístrate aquí")
        }
    }
}

@Preview(showBackground = true, name = "Login Screen Preview")
@Composable
fun LoginScreenPreview() {
    GuardiaTheme {
        LoginScreen()
    }
}
