package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SupervisorActivity : AppCompatActivity() {

    private val nombreSupervisor = "Nombre Supervisor"  // Puedes cambiar por valor dinámico si tienes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supervisor)

        findViewById<ImageButton>(R.id.startShiftButton).setOnClickListener {
            val intent = Intent(this, TomarFotoActivity::class.java)
            intent.putExtra("perfil", "supervisor")
            intent.putExtra("nombre", nombreSupervisor)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.endShiftButton).setOnClickListener {
            val intent = Intent(this, FinTurnoActivity::class.java)
            intent.putExtra("perfil", "supervisor")
            intent.putExtra("nombre", nombreSupervisor)
            startActivity(intent)
        }

        // Aquí agregamos el botón rondines para abrir RondinesActivity
        findViewById<ImageButton>(R.id.rondinesButton).setOnClickListener {
            val intent = Intent(this, RondinesActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.instructionsButton).setOnClickListener {
            val intent = Intent(this, InstruccionesSupervisorActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.reportsButton).setOnClickListener {
            val intent = Intent(this, InformesActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.quickAlertsButton).setOnClickListener {
            val intent = Intent(this, AlertasRapidasActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.incidentsButton).setOnClickListener {
            val intent = Intent(this, IncidenciaActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.logoutButton).setOnClickListener {
            val intent = Intent(this, ConfirmLogoutActivity::class.java)
            startActivity(intent)
        }
    }
}
