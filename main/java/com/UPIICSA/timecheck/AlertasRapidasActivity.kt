package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AlertasRapidasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alertas_rapidas)

        val botones = mapOf(
            R.id.btnInundacion to "Inundación",
            R.id.btnIncendio to "Incendio",
            R.id.btnVidrios to "Ruptura de vidrios",
            R.id.btnApagon to "Apagón",
            R.id.btnFarderos to "Farderos",
            R.id.btnSalud to "Salud",
            R.id.btnOtro to "Otro"
        )

        // Recorrer el mapa para asignar los listeners de los botones
        botones.forEach { (id, tipo) ->
            findViewById<Button>(id).setOnClickListener {
                if (tipo.isNotEmpty()) {
                    val intent = Intent(this, EnviarAlertaActivity::class.java)
                    intent.putExtra("tipoAlerta", tipo)
                    startActivity(intent)
                }
            }
        }
    }
}

