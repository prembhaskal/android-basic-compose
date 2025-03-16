package com.prem.scramble.data

data class WordPair (
    val original: String,
    val scrambled: String
)

data class Level(
    val id: Int,
    val wordPairs: List<WordPair>,
    var completed: Boolean = false
)

data class GameProgress(
    val levelId: Int,
    val solvedWords: List<String>
)