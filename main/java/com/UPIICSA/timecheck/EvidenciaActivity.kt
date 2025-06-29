package com.UPIICSA.timecheck

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EvidenciaActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY = 2

    private lateinit var etDescripcion: EditText
    private lateinit var btnCamara: Button
    private lateinit var btnGaleria: Button
    private lateinit var btnEnviar: Button
    private lateinit var imagenAdjunta: ImageView

    private var imagenTomada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evidencia)

        val tvEncabezado = findViewById<TextView>(R.id.tvEncabezado)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnCamara = findViewById(R.id.btnCamara)
        btnGaleria = findViewById(R.id.btnGaleria)
        btnEnviar = findViewById(R.id.btnEnviar)

        // Recibir datos del punto
        val titulo = intent.getStringExtra("titulo") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""

        tvEncabezado.text = "Evidencias"
        tvDescripcion.text = descripcion

        btnCamara.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }

        btnGaleria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_GALLERY)
        }

        btnEnviar.setOnClickListener {
            val descripcionTexto = etDescripcion.text.toString().trim()

            if (!imagenTomada || descripcionTexto.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aquí podrías guardar la evidencia en base de datos o backend

            // Retornar resultado OK para marcar como verificado
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as? Bitmap
                    if (bitmap != null) {
                        imagenTomada = true
                        // Puedes mostrar la imagen si quieres
                        Toast.makeText(this, "Imagen tomada con cámara", Toast.LENGTH_SHORT).show()
                    }
                }
                REQUEST_GALLERY -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        imagenTomada = true
                        Toast.makeText(this, "Imagen seleccionada de galería", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
