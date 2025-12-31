package com.prem.scramble.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prem.scramble.R
import com.prem.scramble.ui.theme.ScrambleTheme

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(),
) {

    val gameUiState by gameViewModel.gameState.collectAsState()

    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    val levelData = gameUiState.levelData
    val levelUserData = gameUiState.userLevelData

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "UNSCRAMBLE",
            style = typography.titleLarge,
        )

        // add a line separator


            // fori loop in kotlin
        for ( wordIdx in 0..3) {
            val scrambledWord = levelData.puzzles.get(wordIdx).scrambledWord
            val userGuess = levelUserData.puzzleInputs[wordIdx].puzzle
            val isGuessRight = levelUserData.puzzleInputs[wordIdx].isCorrect
//            WordLayout(
//                modifier = Modifier,
//                scrambledWord = scrambledWord,
//                userGuess = userGuess,
//                userGuessChanged = {gameViewModel.onInputChanged(wordIdx, it)},
//                isGuessRight
//            )
            WordLayout2(
                modifier = Modifier,
                scrambledWord = scrambledWord,
                userGuess = userGuess,
                userGuessChanged = {gameViewModel.onInputChanged(wordIdx, it)},
                isGuessRight
            )
//            ScrambleWordRow(
//                modifier = Modifier,
//                scrambledWord = scrambledWord,
//                circlePositions = levelData.puzzles.get(wordIdx).circledPositions,
//                userAnswer = levelUserData.puzzleInputs[wordIdx].puzzle,
//                onAnswerChange = { gameViewModel.onInputChanged(wordIdx, it) }
//                )
        }

        RiddleSection(
            riddleParts = gameUiState.riddleParts,
            filledRiddleParts = gameUiState.filledRiddleParts,
            userRiddleAnswers = levelUserData.riddleAnswers,
            expectedRiddleAnswers = levelData.riddleAnswers,
            onRiddleAnswerChange = { blankIdx, value ->
                gameViewModel.onRiddleAnswerChanged(blankIdx, value)
            },
            modifier = Modifier.padding(top = mediumPadding)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button (
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    gameViewModel.onSubmitClicked()
                }
            ) {
                Text(
                    text = "submit",
                    fontSize = 16.sp
                )
            }
        }

        GameStatus(modifier = Modifier.padding(20.dp), levelUserData.isLevelSolved)

    }
}

@Composable
fun GameStatus(modifier: Modifier = Modifier, isSolved: Boolean = false) {
    val solvedStatus : String
    if (isSolved) {
        solvedStatus = "Level Complete"
    } else {
        solvedStatus = "Level Incomplete"
    }

    Card(
        modifier = modifier
    ) {
        Text(
            text = solvedStatus,
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

// unused for now.
@Composable
fun GameLayout(
    onUserGuessChanged: (String) -> Unit,
    isGuessWrong: Boolean,
    userGuess: String,
    onKeyboardDone: () -> Unit,
    currentScrambledWord: String,
    modifier: Modifier = Modifier) {
    // show current level with all scrambled words
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text (
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.word_count, 0),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentScrambledWord,
                fontSize = 45.sp,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "instructions",
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessWrong) {
                        Text("wrong guess")
                    } else {
                        Text("enter your word") }
                    },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone()}
                )
            )
        }
    }
}

// WordLayout represents 1 row with scrambledWord on left,
// then userGuess on its right,
// then icon for right/wrong/unanswered
@Composable
fun WordLayout(
    modifier: Modifier = Modifier,
    scrambledWord: String,
    userGuess: String,
    userGuessChanged: (String) -> Unit,
    isGuessRight: Boolean = false) {
    // row with scrambled words, actual word + icon for right/wrong/unanswered

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = scrambledWord,
            fontSize = 24.sp,
            modifier = Modifier.weight(0.4f)
        )
        OutlinedTextField(
            value = userGuess,
            onValueChange = userGuessChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            singleLine = true,
            label = { Text("fill it") },
            colors = TextFieldDefaults.colors(),
            shape = shapes.medium
        )
        if (!isGuessRight) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "wrong",
                modifier = Modifier.weight(0.1f)
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "right",
                modifier = Modifier.weight(0.1f)
            )
        }

    }
}

@Composable
fun WordLayout2(
    modifier: Modifier = Modifier,
    scrambledWord: String,
    userGuess: String,
    userGuessChanged: (String) -> Unit,
    isGuessRight: Boolean = false) {
    // row with scrambled words, actual word + icon for right/wrong/unanswered

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = scrambledWord,
            fontSize = 20.sp,
            modifier = Modifier.weight(0.4f)
        )
        ScrambleRowInput(
            cellCount = scrambledWord.length,
            circledIndices = listOf(1, 2),
            value = userGuess,
            onValueChange = userGuessChanged,
            stroke = 1.dp,
        )
        if (!isGuessRight) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "wrong",
                modifier = Modifier.weight(0.1f)
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "right",
                modifier = Modifier.weight(0.1f)
            )
        }

    }
}

@Composable
fun ScrambleWordRow(
    modifier: Modifier = Modifier,
    scrambledWord: String,
    circlePositions: List<Int>,
    userAnswer: String,
    onAnswerChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // Click anywhere on row to focus
                focusRequester.requestFocus()
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Scrambled word label
        Text(
            text = scrambledWord,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // This is the key part - overlay approach
        Box {
            // Visual letter boxes (non-interactive)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(scrambledWord.length) { index ->
                    LetterDisplayBox(
                        isCircle = circlePositions.contains(index),
                        letter = userAnswer.getOrNull(index)?.toString() ?: "",
                        isFocused = isFocused,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // Hidden text field that captures all input
            BasicTextField(
                value = userAnswer,
                onValueChange = { newValue ->
                    // Limit to word length and convert to uppercase
                    if (newValue.length <= scrambledWord.length) {
                        onAnswerChange(newValue.uppercase())
                    }
                },
                textStyle = TextStyle(
                    color = Color.Transparent, // Make text invisible
                    fontSize = 1.sp
                ),
                cursorBrush = SolidColor(Color.Transparent), // Hide cursor
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Characters
                ),
                onTextLayout = { },
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused }
            )
        }
    }
}


@Composable
fun LetterDisplayBox(
    isCircle: Boolean,
    letter: String,
    isFocused: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .then(
                if (isCircle) {
                    Modifier
                        .border(
                            width = if (isFocused) 3.dp else 2.dp,
                            color = if (isFocused) Color.Blue else Color.Black,
                            shape = CircleShape
                        )
                        .background(Color.White, CircleShape)
                }
                else {
                    Modifier
                        .border(
                            width = if (isFocused) 3.dp else 2.dp,
                            color = if (isFocused) Color.Blue else Color.Black,
                            shape = RectangleShape
                        )
                        .background(Color.White, RectangleShape)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun ScrambleRowInput(
    cellCount: Int,
    circledIndices: List<Int>,
    value: String,
    onValueChange: (String) -> Unit,
    cellSize: Dp = 44.dp,
    stroke: Dp = 2.dp,
) {
    val maxLen = cellCount

    BasicTextField(
        value = value,
        onValueChange = { new ->
            // keep only letters, upper-case, and limit length
            val filtered = new
                .filter { it.isLetter() }
                .uppercase()
                .take(maxLen)
            onValueChange(filtered)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            keyboardType = KeyboardType.Ascii
        ),
        cursorBrush = SolidColor(Color.Transparent), // optional: hide default cursor
        decorationBox = { innerTextField ->
            // We still need the inner field so IME works, but we visually render cells.
            Box {
                Row {
                    repeat(cellCount) { i ->
                        val ch = value.getOrNull(i)?.toString().orEmpty()

                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .border(stroke, Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            if (i in circledIndices) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp)
                                        .border(stroke, Color.Black, CircleShape)
                                )
                            }
                            Text(
                                text = ch,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }

                // Keep the real text field layered on top (or behind) to capture input.
                // Make it invisible but focusable/clickable.
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.01f) // "invisible" but still receives touch/IME
                ) {
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun RiddleSection(
    riddleParts: List<RiddlePart>,
    filledRiddleParts: List<RiddlePart>,
    userRiddleAnswers: List<String>,
    expectedRiddleAnswers: List<String>,
    onRiddleAnswerChange: (blankIdx: Int, value: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 2 columns
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // first column
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.weight(1f),
        ) {
            Text (text = "HOW TO PLAY", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
            Text("Now arrange  the letters in the circles to form the answer to the " +
                    "riddle or to fill in the missing word as indicated")
        }

        // 2nd column
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.weight(1f),
        ) {
            // rectangle enclosing the texxt
            Row(
                modifier = Modifier.padding(top=16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Black)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    val riddleDisplay = filledRiddleParts.joinToString(separator = "") { it.word }
                    Text(riddleDisplay)
                }
            }
        }
    }

    val blankCount = riddleParts.count { it.wordType == WordType.BLANK }

    for (blankIdx in 0 until blankCount) {
        val expectedLen = expectedRiddleAnswers.getOrNull(blankIdx)?.length
            ?: riddleParts.filter { it.wordType == WordType.BLANK }.getOrNull(blankIdx)?.word?.length
            ?: 0

        val value = userRiddleAnswers.getOrNull(blankIdx).orEmpty()

        OutlinedTextField(
            value = value,
            onValueChange = { new ->
                val cleaned = new
                    .filter { it.isLetter() }
                    .uppercase()
                    .take(expectedLen)
                onRiddleAnswerChange(blankIdx, cleaned)
            },
            label = { Text("Word ${blankIdx + 1} (${expectedLen})") },
            supportingText = { Text("${value.length}/${expectedLen}") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    ScrambleTheme {
        GameScreen()
    }
}