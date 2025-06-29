package com.UPIICSA.timecheck

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ConfirmarFotoActivity : AppCompatActivity() {

    private lateinit var perfil: String
    private lateinit var nombre: String
    private lateinit var fotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_foto)

        val selfieImage = findViewById<ImageView>(R.id.selfieImage)
        val usernameText = findViewById<TextView>(R.id.usernameText)
        val dateTimeText = findViewById<TextView>(R.id.dateTimeText)
        val changePhotoButton = findViewById<Button>(R.id.changePhotoButton)
        val confirmPhotoButton = findViewById<Button>(R.id.confirmPhotoButton)

        perfil = intent.getStringExtra("perfil") ?: "guardia"
        nombre = intent.getStringExtra("nombre") ?: "Usuario"
        fotoPath = intent.getStringExtra("fotoPath") ?: ""

        // Personalizar saludo
        usernameText.text = if (perfil == "supervisor") {
            "Supervisor $nombre"
        } else {
            "Guardia $nombre"
        }

        val sdf = SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy, HH:mm:ss", Locale("es", "MX"))
        dateTimeText.text = sdf.format(Date())

        if (fotoPath.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeFile(fotoPath)
            selfieImage.setImageBitmap(bitmap)
        } else {
            Toast.makeText(this, "No se encontró la foto.", Toast.LENGTH_SHORT).show()
        }

        changePhotoButton.setOnClickListener {
            val intent = Intent(this, TomarFotoActivity::class.java)
            intent.putExtra("perfil", perfil)
            intent.putExtra("nombre", nombre)
            startActivity(intent)
            finish()
        }

        confirmPhotoButton.setOnClickListener {
            val intent = Intent(this, InicioConfirmadoActivity::class.java)
            intent.putExtra("perfil", perfil)
            intent.putExtra("nombre", nombre)
            startActivity(intent)
            finish()
        }
    }
}
