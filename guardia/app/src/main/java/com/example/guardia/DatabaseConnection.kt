package com.example.guardia
// Clase para manejar la conexión a la base de datos.
//importamos las librerias necesarias
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.security.MessageDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Clase para manejar la conexión a la base de datos.
object DatabaseConnection {
    fun getConnection(): Connection? {
       // val url = "jdbc:mysql://srv805.hstgr.io:3306/u666383048_asistencia"
        val url = "jdbc:mysql://srv805.hstgr.io:3306/u666383048_asistencia?sslMode=DISABLED"
        val user = "u666383048_asistencia"
        val password = "4UHFBjPE@a"

        return try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            DriverManager.getConnection(url, user, password)
        } catch (e: Exception) {
            println("Error de conexión a la base de datos:")
            e.printStackTrace()
            null
        }
    }
}

// Función para hashear la contraseña
fun hashPassword(password: String): String {
    val bytes = password.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}

/**
 * Registra un nuevo usuario en la base de datos.
 * @return Null si es exitoso, o un mensaje de error si falla.
 */
suspend fun registerUser(
    correo: String,
    contrasena: String,
    rol: String,
    telefono: String? = null,
    foto: String? = null
): String? = withContext(Dispatchers.IO) {
    val connection = DatabaseConnection.getConnection()
    if (connection == null) {
        return@withContext "Error: No se pudo conectar a la base de datos."
    }

    val contrasenaHash = hashPassword(contrasena)
    val sql = "INSERT INTO usuarios (correo, contrasena, rol, telefono, foto) VALUES (?, ?, ?, ?, ?)"

    return@withContext try {
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, correo)
            statement.setString(2, contrasenaHash)
            statement.setString(3, rol)
            statement.setString(4, telefono)
            statement.setString(5, foto)

            val rowsAffected = statement.executeUpdate()
            if (rowsAffected > 0) {
                null
            } else {
                "Error desconocido al crear la cuenta."
            }
        }
    } catch (e: SQLException) {
        println("SQL Exception: ${e.sqlState} - ${e.message}")
        if (e.message?.contains("Duplicate entry") == true && e.message?.contains("'correo'") == true) {
            "El correo electrónico ya está registrado."
        } else {
            "Error en la base de datos: ${e.message}"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Error inesperado: ${e.message}"
    } finally {
        connection.close()
    }
}

/**
 * Inicia sesión para un usuario, verificando sus credenciales.
 * @return Null si el inicio de sesión es exitoso, o un mensaje de error si falla.
 */
suspend fun loginUser(correo: String, contrasena: String): String? = withContext(Dispatchers.IO) {
    val connection = DatabaseConnection.getConnection()
    if (connection == null) {
        return@withContext "Error: No se pudo conectar a la base de datos."
    }

    val sql = "SELECT contrasena FROM usuarios WHERE correo = ?"
    return@withContext try {
        connection.prepareStatement(sql).use { statement ->
            statement.setString(1, correo)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                val storedPasswordHash = resultSet.getString("contrasena")
                if (storedPasswordHash == hashPassword(contrasena)) {
                    null // Inicio de sesión exitoso
                } else {
                    "Credenciales incorrectas."
                }
            } else {
                "Correo no registrado."
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Error inesperado: ${e.message}"
    } finally {
        connection.close()
    }
}