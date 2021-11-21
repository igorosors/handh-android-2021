package com.example.lesson_5_strelyukhin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat

class Activity4 : AppCompatActivity() {
    companion object {
        private const val KEY_TIME = "key_time"

        fun createStartIntent(context: Context, time: Long): Intent {
            return Intent(context, Activity4::class.java).apply {
                putExtra(KEY_TIME, time)
            }
        }
    }

    private val sdf by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }
    private val timeView by lazy { findViewById<TextView>(R.id.timeView) }
    private val from4to4 by lazy { findViewById<Button>(R.id.from4to4) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_4)

        val time = intent.getLongExtra(KEY_TIME, 0)
        timeView.text = sdf.format(time)

        from4to4.setOnClickListener {
            startActivity(createStartIntent(this, System.currentTimeMillis()))
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)


        val time = intent?.getLongExtra(KEY_TIME, 0)
        timeView.text = sdf.format(time)
    }
}