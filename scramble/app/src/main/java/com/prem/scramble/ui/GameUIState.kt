package com.prem.scramble.ui

import com.prem.scramble.data.Level

data class GameUIState(
    // TODO add time to solve the level in milliseconds
    val currentLevel: Int = 0,
    val levelData: Level = Level(0, emptyList(), false),
    // add currentInputs as an array of 4 string
//    val currentInputs: Array<String> = arrayOf("", "", "", ""),
    val currentInputs: MutableList<String> = mutableListOf(),
    val isGameOver: Boolean = false,

)
