package com.prem.scramble.ui

import androidx.compose.runtime.mutableStateListOf

class Riddle(
    val parts: List<RiddlePart>,
    val attribution: String,
    private val onStringChange: (String) -> Unit
) {
    // Track answers for each blank
    private val answers = mutableStateListOf<String>().apply {
        // Initialize with empty strings for each blank
        repeat(parts.count { it is RiddlePart.Blank }) { add("") }
    }

    // Get current answer for a blank by index
    fun getAnswer(blankIndex: Int): String = answers.getOrElse(blankIndex) { "" }

    // Called when user types in a blank
    fun onBlankChange(blankIndex: Int, value: String) {
        if (blankIndex in answers.indices) {
            answers[blankIndex] = value
            onStringChange(buildFullString())
        }
    }

    // Reconstruct the full sentence
    fun buildFullString(): String = buildString {
        var blankIdx = 0
        parts.forEach { part ->
            when (part) {
                is RiddlePart.Text -> append(part.content)
                is RiddlePart.Blank -> {
                    val answer = answers.getOrElse(blankIdx) { "" }
                    append(answer.ifEmpty { "_".repeat(part.length) })
                    blankIdx++
                }
            }
        }
    }

    // Get blank info (useful for validation)
    fun getBlankLength(blankIndex: Int): Int {
        var idx = 0
        parts.forEach { part ->
            if (part is RiddlePart.Blank) {
                if (idx == blankIndex) return part.length
                idx++
            }
        }
        return 0
    }
}