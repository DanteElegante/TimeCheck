package com.UPIICSA.timecheck

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class EnviarAlertaActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_GALLERY = 2
    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_STORAGE_PERMISSION = 101

    private lateinit var imagenAdjunta: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_alerta)

        val tipoAlerta = intent.getStringExtra("tipoAlerta") ?: "Alerta"
        findViewById<TextView>(R.id.tipoAlertaText).text = tipoAlerta
        imagenAdjunta = findViewById(R.id.imagenAdjunta)

        // Botón para seleccionar imagen de la galería
        findViewById<Button>(R.id.btnGaleria).setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE_PERMISSION)
            } else {
                abrirGaleria()
            }
        }

        // Botón de cámara (si lo necesitas)
        findViewById<Button>(R.id.btnCamara).setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                abrirCamara()
            }
        }

        findViewById<Button>(R.id.btnEnviarAlerta).setOnClickListener {
            // Simula envío y redirige a la pantalla de confirmación
            val intent = Intent(this, ConfirmacionAlertaActivity::class.java)
            startActivity(intent)
            finish()  // Finaliza la actividad actual
        }
    }

    // Método para abrir la galería y seleccionar una imagen
    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"  // Solo imágenes
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    // Método para abrir la cámara
    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    // Manejar el resultado de la galería
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as? Bitmap
                    bitmap?.let {
                        imagenAdjunta.setImageBitmap(it)
                    }
                }
                REQUEST_GALLERY -> {
                    val imageUri = data?.data
                    imageUri?.let {
                        imagenAdjunta.setImageURI(it)
                    }
                }
            }
        }
    }
}

