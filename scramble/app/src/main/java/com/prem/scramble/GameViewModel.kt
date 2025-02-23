package com.prem.scramble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow<GameState>(GameState.Loading)
    val gameState: StateFlow<GameState> = _gameState

    private val _currentInputs = MutableStateFlow<List<String>>(List(4) { "" })
    val currentInputs: StateFlow<List<String>> = _currentInputs

    private var currentLevel: Level? = null

    init {
        loadGame()
    }

    private fun loadGame() {
        viewModelScope.launch {
            val levels = generateLevels()
            _gameState.value = GameState.Playing(levels[0])
            currentLevel = levels[0]
        }
    }

    fun updateWord(index: Int, word: String) {
        val currentList = _currentInputs.value.toMutableList()
        currentList[index] = word.uppercase()
        _currentInputs.value = currentList
    }

    fun checkSolution() {
        val level = currentLevel ?: return
        val isCorrect = _currentInputs.value.zip(level.words).all { (input, word) ->
            input.equals(word.original, ignoreCase = true)
        }

        if (isCorrect) {
            _gameState.value = GameState.Success
            saveProgress()
        } else {
            _gameState.value = GameState.Error("Not all words are correct. Try again!")
        }
    }

    private fun saveProgress() {
        viewModelScope.launch {
            // Save progress to local storage
            currentLevel?.let { level ->
                val progress = GameProgress(
                    levelId = level.id,
                    solvedWords = _currentInputs.value
                )
                // Implementation for saving to DataStore or Room DB
            }
        }
    }

    private fun generateLevels(): List<Level> {
        return listOf(
            Level(
                id = 1,
                words = listOf(
                    Word("HAPPY", "PAHPY"),
                    Word("CLOUD", "DUCLO"),
                    Word("SMILE", "LISME"),
                    Word("BRAIN", "NBRAI")
                )
            ),
            // Add more levels here
        )
    }
}

sealed class GameState {
    object Loading : GameState()
    data class Playing(val level: Level) : GameState()
    object Success : GameState()
    data class Error(val message: String) : GameState()
}
