package com.example.lesson_5_strelyukhin

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class Activity5 : AppCompatActivity() {
    companion object {

        private const val KEY_DATA = "key_data"
        const val EXTRA_RESULT_INPUT = "extra_result_input"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, Activity5::class.java)
        }
    }


    private val deliverResult by lazy { findViewById<Button>(R.id.deliverResult) }
    private val editText by lazy { findViewById<EditText>(R.id.editText) }
    private val btnSave by lazy { findViewById<Button>(R.id.btnSave) }
    private val editData by lazy { findViewById<EditText>(R.id.editData) }
    private val viewData by lazy { findViewById<TextView>(R.id.viewData) }
    private var data = Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_5)

        deliverResult.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(EXTRA_RESULT_INPUT, editText.text.toString())
            })
            finish()
        }

        btnSave.setOnClickListener {
            data.value = editData.text.toString()
            viewData.text = data.value
        }
        restoreToView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_DATA, data)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreToView(savedInstanceState)
    }

    private fun restoreToView(savedInstanceState: Bundle?) {
        val saved = savedInstanceState?.getParcelable<Data>(KEY_DATA)
        if (saved != null) {
            data = saved
            viewData.text = data.value
        }
    }
}

