package com.example.lesson_2_strelyukhin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAct2 = findViewById<Button>(R.id.btnAct2)
        val btnAct3 = findViewById<Button>(R.id.btnAct3)

        btnAct2.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }

        btnAct3.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }

    }
}