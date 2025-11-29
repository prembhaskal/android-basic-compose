package com.prem.scramble.ui

import com.prem.scramble.data.LevelData
import com.prem.scramble.data.UserLevelData

data class GameUILevelState(
    // GameUILevelState is holding only single game level information.
    val currentLevel: Int = 0,
    val levelData: LevelData = emptyLevelData(),
    val userLevelData: UserLevelData = emptyUserLevelData(),
    )


fun emptyLevelData(): LevelData {
    return LevelData(0, "", emptyList(), "", emptyList(), "")
}
fun emptyUserLevelData(): UserLevelData {
//    val emptyStringList: List<String> = emptyList()
    return UserLevelData(0, emptyLevelData(), emptyList(), "", false)
}

