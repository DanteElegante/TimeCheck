package com.UPIICSA.timecheck

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class InstruccionesGuardiaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucciones)

        val okButton = findViewById<Button>(R.id.okButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        val goToMenuIntent = Intent(this, GuardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        okButton.setOnClickListener {
            startActivity(goToMenuIntent)
            finish()
        }

        backButton.setOnClickListener {
            startActivity(goToMenuIntent)
            finish()
        }
    }
}
