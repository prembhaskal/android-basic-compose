package com.prem.spellathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prem.spellathon.ui.theme.SpellathonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpellathonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SpellathonLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SpellathonLayout( modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
        ) {
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            ) {
            Text(
                text = "Spellathon",
                modifier = modifier
            )
        }
        Row (
            modifier = modifier,
        ) {
            ImageSection(Modifier.weight(1f))
            RuleSection(Modifier.weight(1f))
        }
        Row(modifier = modifier) {
            InputWordSection()
        }
    }
}

@Composable
private fun RuleSection(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Rules",
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            color = Color.Blue,
        )
        Text(
            text = "How many words of four or more letters can you make from the letters shown in today's puzzle?" +
                    "In making a word, each letter may be used once only. Each word must contain the central letter." +
                    "There should be at least one seven-letter word. Plurals, foreign words and proper names are not allowed.",
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            textAlign = TextAlign.Justify,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun ImageSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .requiredSize(175.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.spell_1),
            contentDescription = "Spell puzzle",
            modifier = modifier
//                .fillMaxSize()
//                .fillMaxWidth()
                .requiredSize(160.dp)
                .padding(top = 10.dp)
        )
    }
}

@Composable
fun InputWordSection(modifier: Modifier = Modifier) {
    var word by remember { mutableStateOf("") }
    var storedTexts by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier,
    ) {
        TextField(
            value = word,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.keyboard_24dp), null)},
            onValueChange = { word = it },
            label = {Text(stringResource(R.string.input_word))},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
            ),
            modifier = modifier,
        )
    }
    Column(
        modifier = Modifier.padding(top = 10.dp),
    ) {
        Button(onClick = { println("input word " + word) }) {
            Text("Add")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpellathonTheme {
        SpellathonLayout()
    }
}