package com.prem.spellathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
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
                Scaffold(
                    topBar = {},
                    bottomBar = {},
                ) { paddingValues -> // padding value to keep things within box
                    SpellathonLayoutMain(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun SpellathonLayoutMain(modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            ),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
    ) {
        SpellathonLayout()
    }
}

// Don't remove, we can fallback to simple box inside scaffold if surface has problem
//@Composable
//fun SpellathonLayoutMain1() {
//    Scaffold(
//        topBar = {},
//        bottomBar = {},
//    ) {paddingValues ->
//        Box(modifier = Modifier.padding(paddingValues)) {
//            SpellathonLayout()
//        }
//    }
//}

@Composable
fun SpellathonLayout(modifier: Modifier = Modifier) {
    val inputwords = remember { mutableStateListOf<WordItem>() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            // TODO move to Top / App Bar
            Text(
                text = "Spellathon", modifier = modifier
            )
        }
        Row(
            modifier = modifier,
        ) {
            ImageSection(Modifier.weight(1f))
            RuleSection(Modifier.weight(1f))
        }
        // Weight for input words helps to expand it
        Row(modifier = modifier.weight(1f)) {
            displayInputWords(modifier, wordItems = inputwords)
        }
        // no weight effectively pins it at bottom
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InputWordSection(inputwords = inputwords)
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
            text = "How many words of four or more letters can you make from the letters shown in today's puzzle?" + "In making a word, each letter may be used once only. Each word must contain the central letter." + "There should be at least one seven-letter word. Plurals, foreign words and proper names are not allowed.",
            modifier = Modifier.padding(start = 5.dp, end = 5.dp),
            textAlign = TextAlign.Justify,
            fontSize = 12.sp,
        )
    }
}

@Composable
private fun ImageSection(modifier: Modifier) {
    Column(
        modifier = modifier.requiredSize(175.dp),
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
fun InputWordSection(modifier: Modifier = Modifier, inputwords: SnapshotStateList<WordItem>) {
    var word by remember { mutableStateOf("") }
    var idCounter by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            value = word,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.keyboard_24dp), null) },
            onValueChange = {
                word = it
                word = word.uppercase()
                word = word.trim()
            },
            label = { Text(stringResource(R.string.input_word)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
            ),
            maxLines = 1,
            modifier = modifier
                .weight(1f)
                .padding(start = 4.dp),
        )
        Button(
            modifier = Modifier.height(48.dp),
            onClick = {
                if (word.length == 0) {
                    println("ignoring empty word")
                } else {
                    idCounter++
                    println("input word $word with id $idCounter")
                    inputwords.add(WordItem(idCounter, word))
                    word = ""
                }
            },
        ) {
            Text("Add")
        }
    }
}

@Composable
private fun displayInputWords(
    modifier: Modifier = Modifier,
    wordItems: SnapshotStateList<WordItem>,
) {

    // uncomment below line for testing layout in preview
//    for (i in 1..30) {
//        wordItems.add(WordItem(i, "ARROW" + i))
//    }

    LazyColumn(
        modifier = Modifier.padding(top = 10.dp),
        reverseLayout = true,
    ) {
        items(wordItems) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF5F5F5), // light gray background
                        shape = RoundedCornerShape(8.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(0.5f),
                    text = item.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF2C2C2C),
                )
                IconButton(
                    modifier = Modifier
                        .weight(0.5f)
                        .wrapContentWidth(Alignment.Start),
                    onClick = {
                        println("deleting word ${item.text} with id ${item.id}")
                        wordItems.remove(item)
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "delete",
                    )
                }
            }
        }
    }
}

data class WordItem(
    val id: Int, val text: String
)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpellathonTheme {
        SpellathonLayout()
    }
}