package com.prem.scramble.ui

import com.prem.scramble.data.GameData
import com.prem.scramble.data.UserGameData

data class GameUIState(
    // not used as of now , using GameUILevelState instead

    // This holds all gameData, but currently it is not really clean on updating single level data
    // may be we need a separate variable indicating current level data, need to think more
    val gameData: GameData = GameData(emptyList()),
    val userGameData: UserGameData = UserGameData(emptyList(), 0, 0),

)
