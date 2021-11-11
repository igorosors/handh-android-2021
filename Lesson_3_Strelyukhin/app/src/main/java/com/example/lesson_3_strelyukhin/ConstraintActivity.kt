package com.example.lesson_3_strelyukhin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar

class ConstraintActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, ConstraintActivity::class.java)
        }
    }

    private val imageViewEdit by lazy { findViewById<ImageView>(R.id.imageViewEdit) }
    private val textViewExit by lazy { findViewById<TextView>(R.id.textViewExit) }
    private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint)

        imageViewEdit.setOnClickListener {
            Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show()
        }

        textViewExit.setOnClickListener {
            Toast.makeText(this, "exit", Toast.LENGTH_SHORT).show()
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.menu.findItem(R.id.edit).setOnMenuItemClickListener {
            Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show()
            true
        }

    }
}