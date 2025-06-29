package com.UPIICSA.timecheck

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuardActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guard)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val startShiftButton = findViewById<ImageButton>(R.id.startShiftButton)
        val endShiftButton = findViewById<ImageButton>(R.id.endShiftButton)
        val overtimeButton = findViewById<ImageButton>(R.id.overtimeButton)
        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        val instructionsButton = findViewById<ImageButton>(R.id.instructionsButton)
        val incidentsButton = findViewById<ImageButton>(R.id.incidentsButton)
        val quickAlertsButton = findViewById<ImageButton>(R.id.quickAlertsButton)
        val titleText = findViewById<TextView>(R.id.titleText)

        val isMessageShown = sharedPreferences.getBoolean("welcomeMessageShown", false)

        if (!isMessageShown) {
            titleText.text = "Bienvenido, Guardia"
            sharedPreferences.edit().putBoolean("welcomeMessageShown", true).apply()
        }

        startShiftButton.setOnClickListener {
            startActivity(Intent(this, TomarFotoActivity::class.java))
        }

        overtimeButton.setOnClickListener {
            startActivity(Intent(this, HorasExtraActivity::class.java))
        }

        endShiftButton.setOnClickListener {
            startActivity(Intent(this, FinTurnoActivity::class.java))
        }

        logoutButton.setOnClickListener {
            startActivity(Intent(this, ConfirmLogoutActivity::class.java))
        }

        instructionsButton.setOnClickListener {
            startActivity(Intent(this, InstruccionesGuardiaActivity::class.java))
        }

        incidentsButton.setOnClickListener {
            startActivity(Intent(this, IncidenciaActivity::class.java))
        }

        // Asegúrate de que el botón de alertas rápidas esté correctamente vinculado
        quickAlertsButton.setOnClickListener {
            startActivity(Intent(this, AlertasRapidasActivity::class.java))
        }
    }
}

