package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConfirmLogoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_logout)  // Aquí debe ir tu layout de confirmación

        val confirmButton = findViewById<Button>(R.id.confirmLogoutButton)
        val cancelButton = findViewById<Button>(R.id.cancelLogoutButton)

        // Acción cuando el usuario confirma cerrar sesión
        confirmButton.setOnClickListener {
            // Aquí puedes agregar la lógica para cerrar sesión, por ejemplo:
            // Para propósitos de demostración, simularemos que el usuario cerró sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Cerrar la actividad actual
        }

        // Acción cuando el usuario cancela el cierre de sesión
        cancelButton.setOnClickListener {
            finish()  // Volver a la actividad anterior
        }
    }
}
