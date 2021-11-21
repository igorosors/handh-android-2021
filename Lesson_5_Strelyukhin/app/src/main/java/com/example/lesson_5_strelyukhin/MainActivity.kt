package com.example.lesson_5_strelyukhin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private val from1to4 by lazy { findViewById<Button>(R.id.from1to4) }
    private val from1to2 by lazy { findViewById<Button>(R.id.from1to2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        from1to4.setOnClickListener {
            startActivity(Activity4.createStartIntent(this, System.currentTimeMillis()))
        }
        from1to2.setOnClickListener {
            startActivity(Activity2.createStartIntent(this))
        }
    }
}