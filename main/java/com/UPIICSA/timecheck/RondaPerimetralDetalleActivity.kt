package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RondaPerimetralDetalleActivity : AppCompatActivity() {

    data class PuntoInspeccion(
        val id: String,
        val titulo: String,
        val descripcion: String,
        var verificado: Boolean = false
    )

    private val puntos = mutableListOf(
        PuntoInspeccion("01", "Oficinas administrativas", "Adjuntar evidencia fotográfica"),
        PuntoInspeccion("02", "Área de mantenimiento", "Adjuntar evidencia fotográfica"),
        PuntoInspeccion("03", "Exterior", "Adjuntar evidencia fotográfica")
    )

    private lateinit var layoutPuntos: LinearLayout
    private lateinit var tvContador: TextView
    private var contadorVerificados = 0
    private val totalPuntos = puntos.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ronda_perimetral_detalle)

        layoutPuntos = findViewById(R.id.layoutPuntos)
        tvContador = findViewById(R.id.tvContador)

        actualizarContador()
        mostrarPuntos()
    }

    private fun actualizarContador() {
        tvContador.text = "Puntos inspeccionados: $contadorVerificados de $totalPuntos"
    }

    private fun mostrarPuntos() {
        layoutPuntos.removeAllViews()
        for (punto in puntos) {
            val btn = Button(this).apply {
                text = "${punto.id} - ${punto.titulo}\n${punto.descripcion}\n${if (punto.verificado) "Verificada" else "No verificada"}"
                setTextColor(if (punto.verificado) 0xFF388E3C.toInt() else 0xFFD32F2F.toInt()) // verde o rojo
                textSize = 14f
                setPadding(12, 12, 12, 12)
                setOnClickListener {
                    // Abrir la pantalla de evidencia para este punto
                    val intent = Intent(this@RondaPerimetralDetalleActivity, EvidenciaActivity::class.java)
                    intent.putExtra("id", punto.id)
                    intent.putExtra("titulo", punto.titulo)
                    intent.putExtra("descripcion", punto.descripcion)
                    startActivityForResult(intent, punto.id.toInt())
                }
            }
            layoutPuntos.addView(btn)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val puntoIndex = puntos.indexOfFirst { it.id.toInt() == requestCode }
            if (puntoIndex != -1) {
                puntos[puntoIndex].verificado = true
                contadorVerificados++
                actualizarContador()
                mostrarPuntos()

                if (contadorVerificados == totalPuntos) {
                    // Todos verificados, regresar resultado OK a la actividad principal
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }
}
