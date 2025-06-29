package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class InicioConfirmadoActivity : AppCompatActivity() {

    private lateinit var perfil: String
    private lateinit var nombre: String
    private lateinit var accion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_confirmado)

        perfil = intent.getStringExtra("perfil") ?: "guardia"
        nombre = intent.getStringExtra("nombre") ?: "Usuario"
        accion = intent.getStringExtra("accion") ?: "iniciar" // Nuevo extra para acción

        val bienvenidaText = findViewById<TextView>(R.id.bienvenidaText)
        val usernameText = findViewById<TextView>(R.id.usernameText)
        val dateTimeText = findViewById<TextView>(R.id.dateTimeText)
        val messageText = findViewById<TextView>(R.id.messageText)
        val backToMenuButton = findViewById<Button>(R.id.backToMenuButton)

        // Personalizar saludo según perfil y acción
        bienvenidaText.text = when (accion) {
            "finalizar" -> if (perfil == "supervisor") "Turno finalizado Supervisor" else "Turno finalizado Guardia"
            else -> if (perfil == "supervisor") "Bienvenido Supervisor" else "Bienvenido Guardia"
        }

        // Texto dinámico que indica inicio o fin de jornada
        messageText.text = when (accion) {
            "finalizar" -> "Haz finalizado tu jornada laboral"
            else -> "Haz iniciado tu jornada laboral"
        }

        usernameText.text = nombre

        val sdf = SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy, HH:mm:ss", Locale("es", "MX"))
        dateTimeText.text = sdf.format(Date())

        backToMenuButton.setOnClickListener {
            val intent = if (perfil == "supervisor") {
                Intent(this, SupervisorActivity::class.java)
            } else {
                Intent(this, GuardActivity::class.java)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
