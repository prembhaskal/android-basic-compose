package com.prem.scramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prem.scramble.data.GameData
import com.prem.scramble.data.UserGameData
import com.prem.scramble.data.UserLevelData
import com.prem.scramble.data.levelData1
import com.prem.scramble.data.levelData2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _gameState = MutableStateFlow(GameUIState())
    val gameState: StateFlow<GameUIState> = _gameState.asStateFlow() // gameState is read-only copy of _gameState

    var userGuess by mutableStateOf("")
        private set


    init {
        resetGame()
    }

    private fun resetGame() {

        // init the game state
        val staticGameData = GameData(listOf(levelData1, levelData2))
        val userLevelData = UserLevelData(
            levelId = 1,
            levelData = levelData1,
            puzzles = mutableListOf("", "", "", ""),
            riddleAnswer = "",
            isLevelSolved = false
        )
        val staticUserGameData = UserGameData(
            allUserLevelData = listOf(userLevelData),
            totalCompletedLevels = 0,
            currentLevel = 0,
        )

        _gameState.value = GameUIState(
            gameData = staticGameData,
            userGameData = staticUserGameData,
        )


//            // When the ViewModel is created, load the user's progress
//            viewModelScope.launch {
//                val loadedUserData = repository.loadUserGameData()
//
//                // Assume you have a way to load static GameData too
//                val staticGameData = loadStaticGameData() // from assets, etc.
//
//                _uiState.value = GameUiState(
//                    totalGameData = TotalGameData(
//                        originalGameData = staticGameData,
//                        userData = loadedUserData
//                    ),
//                    //... other initial state
//                )
//            }

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
//        val currentInputs = _gameState.value.currentInputs
//        currentInputs[wordIdx] = input
//        _gameState.value = _gameState.value.copy(currentInputs = currentInputs)
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
