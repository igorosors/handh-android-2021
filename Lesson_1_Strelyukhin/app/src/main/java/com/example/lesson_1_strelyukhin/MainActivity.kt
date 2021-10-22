package com.example.lesson_1_strelyukhin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.TreeSet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user_field = findViewById<EditText>(R.id.user_field)
        val addbtn = findViewById<Button>(R.id.addbtn)
        val outbtn = findViewById<Button>(R.id.outbtn)
        val textView = findViewById<TextView>(R.id.textView)
        val btnAct = findViewById<Button>(R.id.btnAct)

        val stdList = TreeSet<String>()

        btnAct.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        addbtn.setOnClickListener {
            if (user_field.text.toString().trim() == "") {
                Toast.makeText(this@MainActivity, R.string.empty, Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@MainActivity, R.string.message, Toast.LENGTH_SHORT).show()
                stdList.add(user_field.text.toString())
                user_field.text = null
                textView.text = null
            }

        }

        outbtn.setOnClickListener {
            textView.text = null
            stdList.forEach {
                textView.append(it + "\n")
            }
        }

    }
}