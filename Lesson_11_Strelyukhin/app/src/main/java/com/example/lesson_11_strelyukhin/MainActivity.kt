package com.example.lesson_11_strelyukhin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_11_strelyukhin.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.chartView.setData(
            listOf("04.05", "05.05", "06.05", "07.05", "08.05", "09.05", "10.05", "11.05", "12.05", "13.05", "14.05"),
            List(10) {
                Random.nextInt(0, 100)
            }
        )
        binding.chartView.setOnClickListener {
            binding.chartView.toggleMyAnimation()
        }

        binding.chartView2.setData(
            listOf("04.05", "05.05", "06.05"),
            List(3) {
                Random.nextInt(0, 100)
            }
        )
        binding.chartView2.setOnClickListener {
            binding.chartView2.toggleMyAnimation()
        }

        binding.chartView3.setData(
            listOf("04.05"),
            List(1) {
                Random.nextInt(0, 100)
            }
        )
        binding.chartView3.setOnClickListener {
            binding.chartView3.toggleMyAnimation()
        }

    }
}