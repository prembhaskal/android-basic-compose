package com.prem.scramble.ui

import androidx.lifecycle.ViewModel
import com.prem.scramble.data.gameLevelsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {
    private val _gameState = MutableStateFlow(GameUIState())
    val gameState: StateFlow<GameUIState> = _gameState.asStateFlow()

    init {
        resetGame()
    }

    private fun resetGame() {
        val level = gameLevelsData[1]
        _gameState.value = GameUIState(
            currentLevel = 1,
            levelData = level
        )
    }

    fun  onInputChanged(wordIdx: Int, input: String) {
        val currentInputs = _gameState.value.currentInputs
        currentInputs[wordIdx] = input
        _gameState.value = _gameState.value.copy(currentInputs = currentInputs)
    }

    fun onSubmitClicked() {
        val currentInputs = _gameState.value.currentInputs
        val solvedWords = _gameState.value.levelData.wordPairs
        var isLevelSolved = true
        for (i in currentInputs.indices) {
            if (currentInputs[i] != solvedWords[i].original) {
                isLevelSolved = false
                break
            }
        }

        if (isLevelSolved) {
            val levelData = _gameState.value.levelData
            levelData.completed = true
            _gameState.update { currentState ->
                currentState.copy(levelData = levelData
                )
            }
        } else {
            // Show error message
        }

//        _gameState.update { state ->
//            state.copy(
//
//            )
//        }
    }

//    private suspend fun saveProgress() {
//        currentLevel?.let { level ->
//            val progress = GameProgressEntity(
//                levelId = level.id,
//                completed = true,
//                solvedWords = _currentInputs.value.joinToString(",")
//            )
//            gameDatabase.gameProgressDao().saveProgress(progress)
//        }
//    }

}
