package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmacionIncidenciaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacion_incidencia)

        val sucursal = intent.getStringExtra("sucursal") ?: ""
        val asunto = intent.getStringExtra("asunto") ?: ""

        val bienvenidaText = findViewById<TextView>(R.id.bienvenidaText)
        val usernameText = findViewById<TextView>(R.id.usernameText)
        val messageText = findViewById<TextView>(R.id.messageText)
        val backToMenuButton = findViewById<Button>(R.id.backToMenuButton)

        bienvenidaText.text = "Incidencia enviada"
        usernameText.text = "Sucursal: $sucursal\nAsunto: $asunto"
        messageText.text = "Tu reporte de incidencia ha sido enviado correctamente."

        backToMenuButton.setOnClickListener {
            // Obtener el perfil del usuario desde SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val perfil = sharedPreferences.getString("perfil", "guardia")  // Recuperar el perfil

            val intent = when (perfil) {
                "supervisor" -> Intent(this, SupervisorActivity::class.java)
                else -> Intent(this, GuardActivity::class.java)  // Default: GuardActivity
            }

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()  // Finaliza la actividad actual
        }
    }
}
