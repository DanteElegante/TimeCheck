package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConfirmacionInformeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacion_informe)

        // Configurar el botón de "Continuar"
        findViewById<Button>(R.id.backToMenuButton).setOnClickListener {
            // Redirigir al perfil de Supervisor
            val intent = Intent(this, SupervisorActivity::class.java)
            startActivity(intent)
            finish()  // Finaliza la actividad actual
        }
    }
}

