package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class InicioConfirmadoHorasExtraActivity : AppCompatActivity() {

    private lateinit var perfil: String
    private lateinit var nombre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_confirmado)

        perfil = intent.getStringExtra("perfil") ?: "guardia"
        nombre = intent.getStringExtra("nombre") ?: "Usuario"

        val bienvenidaText = findViewById<TextView>(R.id.bienvenidaText)
        val usernameText = findViewById<TextView>(R.id.usernameText)
        val messageText = findViewById<TextView>(R.id.messageText)
        val dateTimeText = findViewById<TextView>(R.id.dateTimeText)
        val backToMenuButton = findViewById<Button>(R.id.backToMenuButton)

        bienvenidaText.text = "Gracias"

        usernameText.text = nombre

        messageText.text = "Haz comenzado tu jornada laboral\nTus horas extras se han registrado exitosamente"

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
