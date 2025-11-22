package com.prem.scramble.data


// level 1 data

val puzzles1 : List<ScrambledPuzzle> = listOf(
    ScrambledPuzzle(5, "PAHPY", listOf(3, 4)),
    ScrambledPuzzle(5, "DUCLO", listOf(1, 2)),
    ScrambledPuzzle(5, "LISME", listOf(2, 3)),
    ScrambledPuzzle(5, "NBRAI", listOf(0, 1)),
)

val puzzleAns1: List<UnscrambledPuzzle> = listOf(
    UnscrambledPuzzle(5, "HAPPY", listOf(3, 4)),
    UnscrambledPuzzle(5, "CLOUD", listOf(1, 2)),
    UnscrambledPuzzle(5, "SMILE", listOf(2, 3)),
    UnscrambledPuzzle(5, "BRAIN", listOf(0, 1)),
)

val levelData1 : LevelData = LevelData(
    levelId = 1,
    levelName = "Level 1",
    puzzles = puzzles1,
    riddle = "something",
    puzzleAnswers = puzzleAns1,
    riddleAnswer = "something"
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
    riddleAnswer = "something"
)

