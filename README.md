# J-C-G


🔑 Login (Iniciar sesión)
// Estados para guardar datos ingresados
val email = remember { mutableStateOf("") }
val password = remember { mutableStateOf("") }


👉 Aquí se almacenan el correo y la contraseña que el usuario escribe.

if (email.value.isEmpty() || password.value.isEmpty()) {
    Toast.makeText(context, "Faltan campos", Toast.LENGTH_SHORT).show()
} else {
    loginUser(email.value, password.value)
}


👉 Se valida que los campos no estén vacíos. Si están bien → se llama a loginUser.

Button(onClick = { /* Navegar a Register */ }) {
    Text("¿No tienes cuenta? Regístrate aquí")
}


👉 Botón para ir a la pantalla de Registro.

📝 Register (Registro de usuario)
// Estados para datos del nuevo usuario
val name = remember { mutableStateOf("") }
val email = remember { mutableStateOf("") }
val password = remember { mutableStateOf("") }


👉 Aquí se guarda el nombre, correo y contraseña que escribe el usuario.

if (name.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
} else {
    registerUser(name.value, email.value, password.value)
}


👉 Valida que no haya campos vacíos. Si todo está correcto → se llama a registerUser.

Button(onClick = { /* Navegar a Login */ }) {
    Text("¿Ya tienes cuenta? Inicia sesión")
}


👉 Botón para volver a la pantalla de Login.

✅ En resumen:
