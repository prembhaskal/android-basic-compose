package com.prem.scramble.data


// level 1 data

val puzzles1 : List<ScrambledPuzzle> = listOf(
    ScrambledPuzzle(5, "DMAEE", listOf(0, 2, 4)),
    ScrambledPuzzle(5, "EANNS", listOf(0, 2, 3, 4)),
    ScrambledPuzzle(6, "NRUCCH", listOf(0, 4, 5)),
    ScrambledPuzzle(6, "AELGGH", listOf(0, 2, 3)),
)

val puzzleAns1: List<UnscrambledPuzzle> = listOf(
    UnscrambledPuzzle(5, "EDEMA", listOf(0, 2, 4)),
    UnscrambledPuzzle(5, "SENNA", listOf(0, 2, 3, 4)),
    UnscrambledPuzzle(6, "CHURCH", listOf(0, 4, 5)),
    UnscrambledPuzzle(6, "HAGGLE", listOf(0, 2, 3)),
)

val levelData1 : LevelData = LevelData(
    levelId = 1,
    levelName = "Level 1",
    puzzles = puzzles1,
    riddle = "______ occurs in direct proportion to dissatisfaction, but dissatisfaction never ____.",
    puzzleAnswers = puzzleAns1,
    riddleAnswers = listOf("CHANGE", "CHANGES")
)

val puzzles2 : List<ScrambledPuzzle> = listOf(
    ScrambledPuzzle(5, "HEAPS", listOf(3, 4)),
    ScrambledPuzzle(5, "THLIG", listOf(1, 2)),
    ScrambledPuzzle(5, "MREAD", listOf(2, 3)),
    ScrambledPuzzle(5, "TWARE", listOf(0, 1)),
)

val puzzleAns2: List<UnscrambledPuzzle> = listOf(
    UnscrambledPuzzle(5, "SHAPE", listOf(3, 4)),
    UnscrambledPuzzle(5, "LIGHT", listOf(1, 2)),
    UnscrambledPuzzle(5, "DREAM", listOf(2, 3)),
    UnscrambledPuzzle(5, "WATER", listOf(0, 1)),
)

val levelData2 : LevelData = LevelData(
    levelId = 2,
    levelName = "Level 2",
    puzzles = puzzles2,
    riddle = "something",
    puzzleAnswers = puzzleAns2,
    riddleAnswers = listOf("something")
)

