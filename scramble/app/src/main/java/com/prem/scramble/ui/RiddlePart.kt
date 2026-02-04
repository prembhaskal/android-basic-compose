package com.prem.scramble.ui

enum class WordType {
    TEXT,
    BLANK,
}

/**
 * UI-side model for riddles.
 *
 * - For [WordType.TEXT], [word] is the literal text to show.
 * - For [WordType.BLANK], [word] is the placeholder (e.g. "______") whose length defines the blank size.
 */
data class RiddlePart(
    val wordType: WordType,
    val word: String,
)