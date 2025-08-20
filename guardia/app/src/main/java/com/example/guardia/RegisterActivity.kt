package com.example.guardia
// importamos las librerias necesarias
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.guardia.ui.theme.GuardiaTheme
import kotlinx.coroutines.launch

class RegisterActivity : ComponentActivity() { // clase para registrarse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuardiaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterScreen(onNavigateBack = { finish() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onNavigateBack: () -> Unit) { // funcion para registrarse
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold( // scaffold para registrarse
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column( // columna para registrarse
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen de logo
            Image(
                painter = painterResource(id = R.drawable.seg),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField( // campos para registrarse
                value = email,
                onValueChange = { email = it.trim() },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp)) // espacio entre campos

            OutlinedTextField( // campos para registrarse
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp)) // espacio entre campos julio lee bien te toy explicando

            OutlinedTextField( // campos para registrarse
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button( // boton para registrarse
                onClick = {
                    if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) { // si esta vacio se muestra un mensaje
                        Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show() // si las contraseñas no coinciden se muestra un mensaje
                        return@Button
                    }
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "Formato de correo inválido", Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    if (password.length < 6) {
                        Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    isLoading = true // si esta cargando se muestra un circulo de progreso
                    coroutineScope.launch {
                        val errorMessage = registerUser(
                            correo = email,
                            contrasena = password,
                            rol = "alumno"
                        )
                        isLoading = false // si no esta cargando se oculta el circulo de progreso

                        if (errorMessage == null) {
                            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_LONG).show()
                            onNavigateBack()
                        } else {
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(), // boton para registrarse
                enabled = !isLoading,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                if (isLoading) { // si esta cargando se muestra un circulo de progreso
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Registrarse")// boton para registrarse julio presta atencion
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Vista previa de registro") // vista previa de registro
@Composable
fun RegisterScreenPreview() {// vista previa de registro
    GuardiaTheme {
        RegisterScreen(onNavigateBack = {})
    }
}
