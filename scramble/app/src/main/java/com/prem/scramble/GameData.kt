package com.prem.scramble

data class Word (
    val original: String,
    val scrambled: String
)

data class Level(
    val id: Int,
    val words: List<Word>,
    var completed: Boolean = false
)

data class GameProgress(
    val levelId: Int,
    val solvedWords: List<String>
)