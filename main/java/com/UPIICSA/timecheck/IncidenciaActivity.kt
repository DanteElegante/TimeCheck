package com.UPIICSA.timecheck

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class IncidenciaActivity : AppCompatActivity() {

    private lateinit var sucursalInput: EditText
    private lateinit var fechaInput: EditText
    private lateinit var horaInput: EditText
    private lateinit var asuntoInput: EditText
    private lateinit var descripcionInput: EditText
    private lateinit var imagenAdjunta: ImageView
    private lateinit var camaraBtn: Button
    private lateinit var galeriaBtn: Button
    private lateinit var enviarBtn: Button

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_GALLERY = 2
    private val REQUEST_CAMERA_PERMISSION = 100

    private var imageUri: Uri? = null  // Para almacenar URI de imagen adjunta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incidencia)

        sucursalInput = findViewById(R.id.sucursalInput)
        fechaInput = findViewById(R.id.fechaInput)
        horaInput = findViewById(R.id.horaInput)
        asuntoInput = findViewById(R.id.asuntoInput)
        descripcionInput = findViewById(R.id.descripcionInput)
        imagenAdjunta = findViewById(R.id.imagenAdjunta)
        camaraBtn = findViewById(R.id.camaraBtn)
        galeriaBtn = findViewById(R.id.galeriaBtn)
        enviarBtn = findViewById(R.id.enviarBtn)

        // TextWatcher para formato automático fecha dd/MM/yyyy
        fechaInput.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val mask = "##/##/####"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }
                val str = s.toString().replace("[^\\d]".toRegex(), "")
                var formatted = ""
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#') {
                        formatted += m
                        continue
                    }
                    if (i >= str.length) break
                    formatted += str[i]
                    i++
                }
                isUpdating = true
                fechaInput.setText(formatted)
                fechaInput.setSelection(formatted.length)
            }
        })

        // TextWatcher para formato automático hora HH:mm
        horaInput.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val mask = "##:##"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }
                val str = s.toString().replace("[^\\d]".toRegex(), "")
                var formatted = ""
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#') {
                        formatted += m
                        continue
                    }
                    if (i >= str.length) break
                    formatted += str[i]
                    i++
                }
                isUpdating = true
                horaInput.setText(formatted)
                horaInput.setSelection(formatted.length)
            }
        })

        camaraBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                abrirCamara()
            }
        }

        galeriaBtn.setOnClickListener {
            abrirGaleria()
        }

        enviarBtn.setOnClickListener {
            enviarIncidencia()
        }
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "No se encontró aplicación de cámara.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
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
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = data?.extras?.get("data") as? Bitmap
                    bitmap?.let {
                        imagenAdjunta.setImageBitmap(it)
                        imageUri = null
                    }
                }
                REQUEST_IMAGE_GALLERY -> {
                    imageUri = data?.data
                    if (imageUri != null) {
                        try {
                            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                            imagenAdjunta.setImageBitmap(bitmap)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Toast.makeText(this, "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun enviarIncidencia() {
        val sucursal = sucursalInput.text.toString().trim()
        val fecha = fechaInput.text.toString().trim()
        val hora = horaInput.text.toString().trim()
        val asunto = asuntoInput.text.toString().trim()
        val descripcion = descripcionInput.text.toString().trim()

        if (sucursal.isEmpty() || fecha.isEmpty() || hora.isEmpty() || asunto.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarFecha(fecha)) {
            Toast.makeText(this, "Fecha inválida. Usa formato dd/MM/yyyy", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarHora(hora)) {
            Toast.makeText(this, "Hora inválida. Usa formato HH:mm (00:00 a 23:59)", Toast.LENGTH_SHORT).show()
            return
        }

        // Continúa con el envío (ejemplo: abrir pantalla confirmación)
        val intent = Intent(this, ConfirmacionIncidenciaActivity::class.java)
        intent.putExtra("sucursal", sucursal)
        intent.putExtra("asunto", asunto)
        startActivity(intent)

        // Limpiar campos si deseas
        sucursalInput.text.clear()
        fechaInput.text.clear()
        horaInput.text.clear()
        asuntoInput.text.clear()
        descripcionInput.text.clear()
        imagenAdjunta.setImageResource(android.R.color.transparent)
        imageUri = null
    }

    private fun validarFecha(fecha: String): Boolean {
        if (!fecha.matches(Regex("""\d{2}/\d{2}/\d{4}"""))) return false

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.isLenient = false
        return try {
            val date = sdf.parse(fecha)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            val year = calendar.get(Calendar.YEAR)
            year in 1900..2100
        } catch (e: ParseException) {
            false
        }
    }

    private fun validarHora(hora: String): Boolean {
        if (!hora.matches(Regex("""\d{2}:\d{2}"""))) return false

        val partes = hora.split(":")
        if (partes.size != 2) return false

        val hh = partes[0].toIntOrNull() ?: return false
        val mm = partes[1].toIntOrNull() ?: return false

        return (hh in 0..23) && (mm in 0..59)
    }
}



