package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class InformesActivity : AppCompatActivity() {

    private lateinit var listaInformes: LinearLayout
    private lateinit var filterGroup: RadioGroup

    // Lista de informes
    private val informes = listOf(
        Pair("Revisión de extintores del pasillo principal", "Mantenimiento"),
        Pair("Sistema de iluminación del área común", "Mantenimiento"),
        Pair("Rondines nocturnos", "Seguridad")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informes)

        // Asignar las vistas
        listaInformes = findViewById(R.id.listaInformes)
        filterGroup = findViewById(R.id.filterGroup)

        // Configurar el listener para el filtro de informes
        filterGroup.setOnCheckedChangeListener { _, _ -> mostrarInformes() }

        // Mostrar todos por defecto
        findViewById<RadioButton>(R.id.filtroTodos).isChecked = true
        mostrarInformes()
    }

    // Función para mostrar los informes según el filtro seleccionado
    private fun mostrarInformes() {
        val filtro = when (filterGroup.checkedRadioButtonId) {
            R.id.filtroMantenimiento -> "Mantenimiento"
            R.id.filtroSeguridad -> "Seguridad"
            else -> "Todos"
        }

        listaInformes.removeAllViews()

        // Agregar los botones de los informes filtrados
        for ((titulo, categoria) in informes) {
            if (filtro == "Todos" || filtro == categoria) {
                val btn = Button(this)
                btn.text = "$titulo\n$categoria"
                btn.setOnClickListener {
                    // Al hacer clic, redirigimos a la pantalla de detalles del informe
                    val intent = Intent(this, DetalleInformeActivity::class.java)
                    intent.putExtra("titulo", titulo)
                    startActivity(intent)
                }
                listaInformes.addView(btn)
            }
        }
    }
}


