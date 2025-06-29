package com.UPIICSA.timecheck

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

class FinTurnoActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_CAMERA_PERMISSION = 100

    private lateinit var perfil: String
    private lateinit var nombre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomar_foto)  // Reutilizamos layout de tomar foto

        perfil = intent.getStringExtra("perfil") ?: "guardia"
        nombre = intent.getStringExtra("nombre") ?: "Usuario"

        val titleTextView = findViewById<TextView>(R.id.usernameText)
        val instructionTextView = findViewById<TextView>(R.id.instructionText)
        val openCameraButton = findViewById<Button>(R.id.openCameraButton)

        // Cambiamos los textos para finalizar turno
        titleTextView.text = if (perfil == "supervisor") {
            "Finalizar turno Supervisor $nombre"
        } else {
            "Finalizar turno Guardia $nombre"
        }

        instructionTextView.text = "Para terminar tu turno es necesario tomar una foto (selfie) antes de tu salida."

        openCameraButton.text = "Finalizar"

        openCameraButton.setOnClickListener {
            if (!dispositivoTieneCamara()) {
                Toast.makeText(this, "Este dispositivo no tiene cámara disponible.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                abrirCamara()
            }
        }
    }

    private fun dispositivoTieneCamara(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "No se encontró aplicación de cámara.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            bitmap?.let {
                val file = File.createTempFile("selfie_", ".jpg", cacheDir)
                val out = FileOutputStream(file)
                it.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()

                val intent = Intent(this, FinTurnoConfirmadoActivity::class.java)  // Cambiado aquí
                intent.putExtra("fotoPath", file.absolutePath)
                intent.putExtra("perfil", perfil)
                intent.putExtra("nombre", nombre)
                intent.putExtra("accion", "finalizar")  // Indicamos que es fin de turno
                startActivity(intent)
                finish() // Cerramos esta activity para evitar volver atrás
            }
        }
    }
}

