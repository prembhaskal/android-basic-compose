package com.prem.scramble.data

/**
 * Immutable Game data loaded from either local or remote
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
    val riddleAnswers: List<String>,
)

data class ScrambledPuzzle(
    val wordLength: Int,
    val scrambledWord: String,
    val circledPositions: List<Int>,
)

data class UnscrambledPuzzle(
    val wordLength: Int,  // TODO do we need this?
    val unscrambledWord: String,
    val circledPositions: List<Int>, // do we really need this?
)

/**
 * Mutable data from user point of view, but keep properties as val (needed for compose UI to work properly)
 **/
data class UserGameData(
    val allUserLevelData: List<UserLevelData>,
    val totalCompletedLevels: Int,
    val currentLevel: Int,
)

data class PuzzleInput (
    val puzzle: String = "",
    val isCorrect: Boolean = false
)

data class UserLevelData(
    val levelId: Int,
    val levelData: LevelData, // pointer to original LevelData (should we keep a clone here)
//    val puzzles: List<String>, // TODO better name?
    val puzzleInputs: List<PuzzleInput>,
    val riddleAnswers: List<String>,
    val isLevelSolved: Boolean,
)
