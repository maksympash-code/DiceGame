package com.example.dicegame

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dicegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val vm: DiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnStop.isEnabled = false

        binding.btnRoll.setOnClickListener {
            vm.rollDices()
        }

        binding.btnStop.setOnClickListener {
            vm.stopRollDices()
        }

        vm.diceUiState.observe(this){ state ->
            binding.btnRoll.isEnabled = state.isRollEnabled
            binding.btnStop.isEnabled = state.isRollStop
            val imgs = listOf(binding.die1, binding.die2, binding.die3, binding.die4, binding.die5)
            state.values.forEachIndexed{ i, v -> imgs[i].setImageResource(diceRes(v))}
        }
    }


    private fun diceRes(value: Int): Int = when (value) {
        1 -> R.drawable.die1
        2 -> R.drawable.die2
        3 -> R.drawable.die3
        4 -> R.drawable.die4
        5 -> R.drawable.die5
        else -> R.drawable.die6
    }
}