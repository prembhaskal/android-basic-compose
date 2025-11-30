package com.prem.scramble.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
            WordLayout(
                modifier = Modifier,
                scrambledWord = scrambledWord,
                userGuess = userGuess,
                userGuessChanged = {gameViewModel.onInputChanged(wordIdx, it)},
                isGuessRight
            )
        }


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

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    ScrambleTheme {
        GameScreen()
    }
}