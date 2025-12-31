package com.prem.scramble.ui

import com.prem.scramble.data.LevelData
import com.prem.scramble.data.UserLevelData

data class GameUILevelState(
    // GameUILevelState is holding only single game level information.
    val currentLevel: Int = 0,
    val levelData: LevelData = emptyLevelData(),
    val userLevelData: UserLevelData = emptyUserLevelData(),
    )

/**
 * Derived UI representation of the current level's riddle.
 *
 * The current convention is that blanks are represented by runs of underscores, e.g.:
 * `"_____ occurs in direct proportion to dissatisfaction, but dissatisfaction never _____."`
 *
 * We map each underscore-run to a [RiddlePart] of type [WordType.BLANK]. The blank length is taken from
 * [LevelData.riddleAnswers] when available (so you can keep the riddle string "generic"), otherwise we
 * fall back to the underscore run length found in the riddle string.
 */
val GameUILevelState.riddleParts: List<RiddlePart>
    get() = levelData.toRiddleParts()

/**
 * Same as [riddleParts], but with user answers applied into BLANK parts.
 *
 * BLANK words remain the same total length; any missing characters are kept as underscores.
 */
val GameUILevelState.filledRiddleParts: List<RiddlePart>
    get() = fillRiddleParts(
        baseParts = riddleParts,
        userAnswers = userLevelData.riddleAnswers,
    )

private val UNDERSCORE_RUN = Regex("(_+)")

fun LevelData.toRiddleParts(): List<RiddlePart> {
    val result = mutableListOf<RiddlePart>()
    val r = riddle

    var cursor = 0
    var blankIndex = 0

    for (match in UNDERSCORE_RUN.findAll(r)) {
        val start = match.range.first
        val endExclusive = match.range.last + 1

        // TEXT before the blank
        if (start > cursor) {
            val text = r.substring(cursor, start)
            if (text.isNotEmpty()) {
                result += RiddlePart(wordType = WordType.TEXT, word = text)
            }
        }

        // BLANK placeholder
        val expectedLen = riddleAnswers.getOrNull(blankIndex)?.length
        val underscoreLen = match.value.length
        val len = expectedLen ?: underscoreLen
        result += RiddlePart(wordType = WordType.BLANK, word = "_".repeat(len))

        blankIndex++
        cursor = endExclusive
    }

    // trailing TEXT after last blank
    if (cursor < r.length) {
        val tail = r.substring(cursor)
        if (tail.isNotEmpty()) {
            result += RiddlePart(wordType = WordType.TEXT, word = tail)
        }
    }

    // If there were no blanks in the riddle string, keep it as one TEXT part.
    if (result.isEmpty() && r.isNotEmpty()) {
        result += RiddlePart(wordType = WordType.TEXT, word = r)
    }

    return result
}

private fun fillRiddleParts(
    baseParts: List<RiddlePart>,
    userAnswers: List<String>,
): List<RiddlePart> {
    if (baseParts.isEmpty()) return emptyList()

    val result = ArrayList<RiddlePart>(baseParts.size)
    var blankIdx = 0

    baseParts.forEach { part ->
        when (part.wordType) {
            WordType.TEXT -> result += part
            WordType.BLANK -> {
                val expectedLen = part.word.length
                val user = userAnswers.getOrNull(blankIdx).orEmpty().take(expectedLen)
                val remaining = (expectedLen - user.length).coerceAtLeast(0)
                val filled = buildString {
                    append(user)
                    if (remaining > 0) append("_".repeat(remaining))
                }
                result += part.copy(word = filled)
                blankIdx++
            }
        }
    }

    return result
}


fun emptyLevelData(): LevelData {
    return LevelData(0, "", emptyList(), "", emptyList(), emptyList())
}
fun emptyUserLevelData(): UserLevelData {
//    val emptyStringList: List<String> = emptyList()
    return UserLevelData(0, emptyLevelData(), emptyList(), emptyList(), false)
}

