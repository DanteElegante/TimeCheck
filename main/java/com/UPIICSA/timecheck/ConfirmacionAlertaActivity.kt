package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConfirmacionAlertaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacion_alerta)

        findViewById<Button>(R.id.backToMenuButton).setOnClickListener {
            // Redirigir al menú principal (ajustar según el tipo de usuario)
            startActivity(Intent(this, GuardActivity::class.java))  // O SupervisorActivity según el usuario
            finish()
        }
    }
}
