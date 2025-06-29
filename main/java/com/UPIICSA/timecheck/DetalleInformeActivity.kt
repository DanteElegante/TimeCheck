package com.UPIICSA.timecheck

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DetalleInformeActivity : AppCompatActivity() {

    private val REQ_CAMERA_CHECK = 1
    private val REQ_CAMERA_EXTINTOR = 2

    private lateinit var estadoCheck: ImageView
    private lateinit var fotoExtintor: ImageView

    private var checkRealizado = false
    private var fotoTomada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_informe)

        val titulo = intent.getStringExtra("titulo") ?: "Detalle"
        findViewById<TextView>(R.id.tituloInforme).text = titulo

        estadoCheck = findViewById(R.id.estadoCheck)
        fotoExtintor = findViewById(R.id.fotoExtintor)

        findViewById<Button>(R.id.btnFotoCheck).setOnClickListener {
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQ_CAMERA_CHECK)
        }

        findViewById<Button>(R.id.btnFotoExtintor).setOnClickListener {
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQ_CAMERA_EXTINTOR)
        }

        findViewById<Button>(R.id.btnGuardarCheck).setOnClickListener {
            val check = findViewById<CheckBox>(R.id.checkRevisado)
            if (check.isChecked) {
                checkRealizado = true
                estadoCheck.setImageResource(R.drawable.ic_circle_green)
            } else {
                estadoCheck.setImageResource(R.drawable.ic_circle_red)
            }
        }

        findViewById<Button>(R.id.btnEnviarInforme).setOnClickListener {
            // Enviar informe y mostrar pantalla de confirmación
            startActivity(Intent(this, ConfirmacionInformeActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            bitmap?.let {
                when (requestCode) {
                    REQ_CAMERA_CHECK -> estadoCheck.setImageResource(R.drawable.ic_circle_green)
                    REQ_CAMERA_EXTINTOR -> {
                        fotoExtintor.setImageBitmap(it)
                        fotoTomada = true
                    }
                }
            }
        }
    }
}
