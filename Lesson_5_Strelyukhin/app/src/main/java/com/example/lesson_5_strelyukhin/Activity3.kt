package com.example.lesson_5_strelyukhin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class Activity3 : AppCompatActivity() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, Activity3::class.java)
        }
    }

    private val launcher = registerForActivityResult(Activity5Contract()) { result ->
        val layout = findViewById<View>(R.id.layout3)
        val snackbar: Snackbar = Snackbar.make(layout,result!!,Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
    private val from3to1 by lazy { findViewById<Button>(R.id.from3to1) }
    private val from3to5 by lazy { findViewById<Button>(R.id.from3to5) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        from3to1.setOnClickListener {
            startActivity(MainActivity.createStartIntent(this))
        }
        from3to5.setOnClickListener {
            launcher.launch(Unit)
        }

    }
}
