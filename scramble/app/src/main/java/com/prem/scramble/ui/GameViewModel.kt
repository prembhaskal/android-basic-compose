package com.prem.scramble.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prem.scramble.data.PuzzleInput
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
            puzzleInputs = MutableList(4) { PuzzleInput("", false) },
            riddleAnswer = "",
            isLevelSolved = false
        )
        _gameState.value = GameUILevelState(
            currentLevel = 1,
            levelData = levelData1,
            userLevelData = userLevelData
        )

    }

    fun  onInputChanged(wordIdx: Int, input: String) {
        if (!isValidInput(wordIdx, input)) {
            return
        }
        val inputupper = input.uppercase()
        _gameState.update { currentState ->
            val newPuzzlesInputs = currentState.userLevelData.puzzleInputs.toMutableList()
            val puzzleInput = PuzzleInput(inputupper, false)
            val updatedPuzzleInput = updatePuzzleStatus(wordIdx, puzzleInput)
            newPuzzlesInputs[wordIdx] =  updatedPuzzleInput

            currentState.copy(
                userLevelData = currentState.userLevelData.copy(
                    puzzleInputs = newPuzzlesInputs.toList()
                )
            )
        }
    }

    fun updatePuzzleStatus(wordIdx: Int, input: PuzzleInput): PuzzleInput {
        val levelData = _gameState.value.levelData
        val expPuzzleAnswer = levelData.puzzleAnswers[wordIdx].unscrambledWord
        if (input.puzzle == expPuzzleAnswer) {
            return input.copy(
                isCorrect = true
            )
        }

        return input
    }

    // doValidationsOnInput does basic validation
    // - length not exceeding input scramble
    // - input is letter in english alphabet
    fun isValidInput(wordIdx: Int, input: String): Boolean {
        val userLevelData = _gameState.value.userLevelData
        val puzzle = userLevelData.levelData.puzzles[wordIdx]
        val puzzleLength = puzzle.wordLength

        if (puzzleLength < input.length) {
            return false
        }

        if (!input.all { it.isLetter() }) {
            return false
        }

        return true
    }

    fun onSubmitClicked() {
        var isLevelSolved = true
        val userLevelData = _gameState.value.userLevelData

        val userAnswers = userLevelData.puzzleInputs

        for (userAnswer in userAnswers) {
            if (!userAnswer.isCorrect) {
                isLevelSolved = false
                break
            }
        }

        if (isLevelSolved) {
            _gameState.update { currentState ->
                currentState.copy(
                    userLevelData = currentState.userLevelData.copy(
                        isLevelSolved = true
                    )
                )
            }

            // add log that all puzzles are solved
            Log.d("GameViewModel", "All puzzles are solved")

            // TODO how to show pop up?
            return
        } else {
            Log.d("GameViewModel", "Not all puzzles are solved")
        }
    }

}
