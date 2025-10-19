package com.example.dicegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

data class DiceUI(
    val values: List<Int> = listOf(1, 1, 1, 1, 1),
    val isRolling: Boolean = false
){
    val isRollEnabled: Boolean get() = !isRolling
    val isRollStop: Boolean get() = isRolling
}

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DiceViewModel: ViewModel() {
    private val _diceUiState = MutableLiveData(DiceUI())
    val diceUiState: LiveData<DiceUI> = _diceUiState

    private var rollJob: Job? = null

    fun rollDices(){
        if (diceUiState.value.isRolling) return

        rollJob = viewModelScope.launch{
            setState { it.copy(isRolling = true) }
            while (isActive){
                val newVals = List(5) { Random.nextInt(1, 7) }
                setState { it.copy(values = newVals) }
                delay(500)
            }
        }
    }

    fun stopRollDices(){
        rollJob?.cancel()
        rollJob = null

        setState { it.copy(isRolling = false) }
    }

    private fun setState(reducer: (DiceUI) -> DiceUI) {
        _diceUiState.value = reducer(_diceUiState.value ?: DiceUI())
    }

}