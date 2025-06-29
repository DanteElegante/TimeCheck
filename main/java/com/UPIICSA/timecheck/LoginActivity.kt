package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Botón de login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if (username == "guard" && password == "guard123") {
                // Guardar el perfil como guardia
                editor.putString("perfil", "guardia")
                editor.apply()
                Toast.makeText(this, "Ingreso exitoso como Guardia", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, GuardActivity::class.java)
                startActivity(intent)
                finish()
            } else if (username == "supervisor" && password == "supervisor123") {
                // Guardar el perfil como supervisor
                editor.putString("perfil", "supervisor")
                editor.apply()
                Toast.makeText(this, "Ingreso exitoso como Supervisor", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SupervisorActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
