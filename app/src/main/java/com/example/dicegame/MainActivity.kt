package com.example.dicegame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dicegame.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var rollJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnStop.isEnabled = false

        binding.btnRoll.setOnClickListener {
            rollDices()
        }

        binding.btnStop.setOnClickListener {
            stopRollDices()
        }
    }

    private fun rollDices(){
        if (rollJob != null) return

        binding.btnStop.isEnabled = true
        binding.btnRoll.isEnabled = false

        val diceView = listOf(binding.die1, binding.die2, binding.die3, binding.die4, binding.die5)

        rollJob = lifecycleScope.launch {
            while (isActive){
                diceView.forEach {
                    val v = Random.nextInt(1, 7)
                    it.setImageResource(diceRes(v))
                }
                delay(1_000)
            }
        }
    }

    private fun stopRollDices(){
        rollJob?.cancel()
        rollJob = null

        binding.btnStop.isEnabled = false
        binding.btnRoll.isEnabled = true
    }

    private fun diceRes(value: Int): Int = when (value) {
        1 -> R.drawable.die1
        2 -> R.drawable.die2
        3 -> R.drawable.die3
        4 -> R.drawable.die4
        5 -> R.drawable.die5
        else -> R.drawable.die6
    }

    override fun onStop() {
        super.onStop()

        stopRollDices()
    }
}