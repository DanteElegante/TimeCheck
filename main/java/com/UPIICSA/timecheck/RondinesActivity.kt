package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RondinesActivity : AppCompatActivity() {

    private lateinit var btnRondaPerimetral: Button
    private lateinit var btnInterior: Button
    private lateinit var tvPuntosPerimetral: TextView
    private lateinit var tvPuntosInterior: TextView

    private var estadoPerimetral = false
    private var estadoInterior = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rondines)

        btnRondaPerimetral = findViewById(R.id.btnRondaPerimetral)
        btnInterior = findViewById(R.id.btnInterior)
        tvPuntosPerimetral = findViewById(R.id.tvPuntosPerimetral)
        tvPuntosInterior = findViewById(R.id.tvPuntosInterior)

        actualizarEstado()

        btnRondaPerimetral.setOnClickListener {
            val intent = Intent(this, RondaPerimetralDetalleActivity::class.java)
            startActivityForResult(intent, 1001)
        }

        btnInterior.setOnClickListener {
            val intent = Intent(this, InteriorDetalleActivity::class.java)
            startActivityForResult(intent, 1002)
        }
    }

    private fun actualizarEstado() {
        if (estadoPerimetral) {
            btnRondaPerimetral.text = "Ronda perimetral (Finalizada)"
            tvPuntosPerimetral.text = "Puntos a inspeccionar: 0"
        } else {
            btnRondaPerimetral.text = "Ronda perimetral (No iniciada)"
            tvPuntosPerimetral.text = "Puntos a inspeccionar: 3"
        }

        if (estadoInterior) {
            btnInterior.text = "Interior (Finalizado)"
            tvPuntosInterior.text = "Puntos a inspeccionar: 0"
        } else {
            btnInterior.text = "Interior (No iniciada)"
            tvPuntosInterior.text = "Puntos a inspeccionar: 2"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1001 -> estadoPerimetral = true
                1002 -> estadoInterior = true
            }
            actualizarEstado()

            // Si ambos terminados, abrir la pantalla de confirmación y cerrar esta actividad
            if (estadoPerimetral && estadoInterior) {
                val intent = Intent(this, ConfirmacionRondinesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

