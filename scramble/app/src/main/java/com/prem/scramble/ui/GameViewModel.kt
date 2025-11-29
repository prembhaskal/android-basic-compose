package com.prem.scramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prem.scramble.data.UserLevelData
import com.prem.scramble.data.levelData1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel: ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _gameState = MutableStateFlow(GameUILevelState())
    val gameState: StateFlow<GameUILevelState> = _gameState.asStateFlow() // gameState is read-only copy of _gameState

    var userGuess by mutableStateOf("")
        private set


    init {
        resetGame()
    }

    private fun resetGame() {

        // init the game state
        val userLevelData = UserLevelData(
            levelId = 1,
            levelData = levelData1,
            puzzles = mutableListOf("", "", "", ""),
            riddleAnswer = "",
            isLevelSolved = false
        )
        _gameState.value = GameUILevelState(
            currentLevel = 1,
            levelData = levelData1,
            userLevelData = userLevelData
        )

    }

    fun updateUserGuess(input: String) {
//        onInputChanged(0, input)
        userGuess = input
    }

    fun checkUserGuess() {
//        val expected = _gameState.value.levelData.wordPairs[0].original
//        if (userGuess.equals(expected, ignoreCase = true)) {
//            // log that guess was true
//            Log.d("GameViewModel", "User guess was correct, expected: ${expected}, actual: ${userGuess}")
//        } else {
//            Log.d("GameViewModel", "User guess was correct, expected: ${expected}, actual: ${userGuess}")
//            _gameState.update {
//                currentState -> currentState.copy(isGuessedWordWrong = true)
//            }
//        }
//
//        updateUserGuess("")
    }


    fun  onInputChanged(wordIdx: Int, input: String) {
        _gameState.update { currentState ->
            val newPuzzles = currentState.userLevelData.puzzles.toMutableList()
            newPuzzles[wordIdx] = input
            currentState.copy(
                userLevelData = currentState.userLevelData.copy(
                    puzzles = newPuzzles.toList()
                )
            )
        }
    }


    fun onSubmitClicked() {
//        val currentInputs = _gameState.value.currentInputs
//        val solvedWords = _gameState.value.levelData.wordPairs
//        var isLevelSolved = true
//        for (i in currentInputs.indices) {
//            if (currentInputs[i] != solvedWords[i].original) {
//                isLevelSolved = false
//                break
//            }
//        }
//
//        if (isLevelSolved) {
//            val levelData = _gameState.value.levelData
//            levelData.completed = true
//            _gameState.update { currentState ->
//                currentState.copy(levelData = levelData
//                )
//            }
//            // TODO show success popup message
//            // TODO add logic to go to next level
//        } else {
//            // TODO Show error message
//        }
    }

}
