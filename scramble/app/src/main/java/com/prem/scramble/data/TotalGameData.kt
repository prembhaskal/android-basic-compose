package com.prem.scramble.data

// not really needed, check gemini's answer how we can load differentely
/*
You have asked the perfect question. You are thinking like an architect, and your intuition is spot on.You are absolutely correct. For the flow we just discussed, the TotalGameData wrapper class is not strictly necessary and can be considered redundant.Let's look at why your feeling is right:1.ViewModel's Role: The ViewModel's primary job is to hold state. It can hold references to GameData and UserGameData directly, without needing an intermediate container.2.State Updates: When you update the UI state, you're often only changing the UserGameData. The GameData remains the same. Having to go through TotalGameData adds an extra layer of copy() calls, making the code slightly more verbose.•With TotalGameData:Kotlin_uiState.update { currentState ->
    currentState.copy(
        totalGameData = currentState.totalGameData.copy( // <-- extra copy
            userData = updatedUserData
        )
    )
}•Without TotalGameData:Kotlinkotlin
        _uiState.update { currentState ->
            currentState.copy(userData = updatedUserData) // <-- cleaner
        }Step 2: Populate your UserGameDataLet's fill out UserGameData with some actual properties it needs to track.Kotlin// This could be in your GameViewModel.kt file or a new GameUiState.kt file
data class GameUiState(
    val gameData: GameData? = null, // Static data, nullable until loaded
    val userData: UserGameData = UserGameData(), // Player progress, has a default
    val isGameLoading: Boolean = true,
    val currentScrambledWord: String = "",
    // ... add any other UI-specific properties here
)(The TotalGameData class can now be safely deleted)Step 3: Update the ViewModelThe ViewModel now becomes much cleaner. It manages the GameUiState directly.Kotlin// In your TotalGameData.kt file, which you might rename to GameModels.kt
data class UserGameData(
    val currentLevelId: Int = 1,
    val currentScore: Int = 0,
    val solvedWords: Set<String> = emptySet()
)ConclusionYour analysis is excellent. By removing the TotalGameData wrapper:•You simplify your overall state representation.•You make the state update logic in the ViewModel cleaner and more direct.•You more accurately reflect the reality that GameData and UserGameData are separate concerns that you only bring together inside the GameUiState.This is a great example of refactoring your models to better suit their actual use case. You can confidently remove TotalGameData and structure your GameUiState to hold the references directly.
 */
data class TotalGameData(
    val originalGameData: GameData,
    val userData: UserGameData,
)

/**
 * Immutable Gamedata loaded from either local or remote
 * */
data class GameData(
    val levelsData: List<LevelData>,
)

data class LevelData(
    val levelId: Int,
    val levelName: String,
    val puzzles: List<ScrambledPuzzle>,
    val riddle: String,
    val puzzleAnswers: List<UnscrambledPuzzle>,
    val riddleAnswer: String,
)

data class ScrambledPuzzle (
    val wordLength: Int,
    val scrambledWord: String,
    val circledPositions: List<Int>,
)

data class UnscrambledPuzzle (
    val wordLength: Int,
    val unscrambledWord: String,
    val circledPositions: List<Int>, // do we really need this?
)

data class UserGameData()
