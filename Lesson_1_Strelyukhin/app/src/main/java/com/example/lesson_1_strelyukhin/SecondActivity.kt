package com.example.lesson_1_strelyukhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val infoEdit = findViewById<EditText>(R.id.infoEdit)
        val infoBtn = findViewById<Button>(R.id.infoBtn)
        val infoView = findViewById<TextView>(R.id.infoView)

        val stdMap = HashMap<String, Int>()
        val infoList = ArrayList<String>()

        infoBtn.setOnClickListener {
            if (infoList.size == 0) {
                Toast.makeText(this@SecondActivity, R.string.emptyInfo, Toast.LENGTH_SHORT).show()
            }
            else {
                infoView.text = null
                infoList.forEach() {
                    infoView.append(it + "\n")
                }
            }
        }

        infoEdit.setOnEditorActionListener(
            OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val parts = infoEdit.text.toString().split(" ").toTypedArray()
                stdMap[parts[0]] = System.currentTimeMillis().toInt()
                infoList.add(stdMap.get(parts[0]).toString())
                parts.forEach() {
                    infoList.add(it)
                }
                infoEdit.text = null
                Toast.makeText(this@SecondActivity, R.string.message, Toast.LENGTH_SHORT).show()
                true
            } else false
        })

    }
}