package com.prem.scramble.ui

import com.prem.scramble.data.GameData
import com.prem.scramble.data.UserGameData

data class GameUIState(
//    // TODO add time to solve the level in milliseconds
//    val currentLevel: Int = 0,
//    val levelData: Level = Level(0, emptyList(), false),
//    // add currentInputs as an array of 4 string
////    val currentInputs: Array<String> = arrayOf("", "", "", ""),
//    val currentInputs: MutableList<String> = mutableListOf(),
//    val isGameOver: Boolean = false,
//
//    // for testing only
//    val isGuessedWordWrong: Boolean = false

    val gameData: GameData = GameData(emptyList()),
    val userGameData: UserGameData = UserGameData(emptyList(), 0, 0),

)
