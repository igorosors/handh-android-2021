package com.example.lesson_3_strelyukhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val buttonConstraint by lazy {
        findViewById<Button>(R.id.buttonConstraint)
    }

    private val buttonLinear by lazy {
        findViewById<Button>(R.id.buttonLinear)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonConstraint.setOnClickListener {
            startActivity(ConstraintActivity.createStartIntent(this))
        }

        buttonLinear.setOnClickListener {
            startActivity(LinearActivity.createStartIntent(this))
        }

    }
}

