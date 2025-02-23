package com.prem.scramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.prem.scramble.ui.theme.ScrambleTheme
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrambleTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScrambleTheme {
        GameScreen()
    }
}

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel()
) {
    val gameState by viewModel.gameState.collectAsState()
    val currentInputs by viewModel.currentInputs.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Word Scramble",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        when (val state = gameState) {
            is GameState.Playing -> {
                state.level.words.forEachIndexed { index, word ->
                    WordInput(
                        scrambledWord = word.scrambled,
                        value = currentInputs[index],
                        onValueChange = { viewModel.updateWord(index, it) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = { viewModel.checkSolution() },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .animateContentSize()
                ) {
                    Text("Check Solution")
                }
            }
            is GameState.Success -> {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Congratulations!",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Button(
                            onClick = { /* Navigate to next level */ },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Next Level")
                        }
                    }
                }
            }
            is GameState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            GameState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun WordInput(
    scrambledWord: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Unscramble: $scrambledWord",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { if (it.length <= 5) onValueChange(it) },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}