package com.example.dicegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dicegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnStop.isEnabled = false
    }
}