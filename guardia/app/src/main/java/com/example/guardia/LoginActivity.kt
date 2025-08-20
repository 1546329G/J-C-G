package com.example.guardia
 //importamos las librerias necesarias
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.guardia.ui.theme.GuardiaTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  // con eso ponemos la panatala del login
            GuardiaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() { // funcion para iniciar sesion
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it.trim() },
            label = { Text("Correo electrónico") },
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

        Button( // boton para iniciar sesion
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "El correo y la contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show() // si esta vacio se muestra un mensaje
                    return@Button
                }

                isLoading = true
                coroutineScope.launch {
                    val errorMessage = loginUser(
                        correo = email,
                        contrasena = password
                    )
                    isLoading = false

                    if (errorMessage == null) {
                        Toast.makeText(context, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            if (isLoading) { // si esta cargando se muestra un circulo de progreso
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Ingresar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { // boton para registrarse
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }) {
            Text(" Regístrate aquí")
        }
    }
}

@Preview(showBackground = true, name = "Vista previa de inicio de sesión") // vista previa de inicio de sesion
@Composable
fun LoginScreenPreview() {
    GuardiaTheme {
        LoginScreen()
    }
}
