package com.example.lesson_5_strelyukhin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Activity2 : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, Activity2::class.java)
        }
    }

    private val from2to3 by lazy { findViewById<Button>(R.id.from2to3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        from2to3.setOnClickListener {
            startActivity(Activity3.createStartIntent(this))
        }

    }
}