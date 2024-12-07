package com.prem.spellathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.prem.spellathon.ui.theme.SpellathonTheme
import kotlin.math.cos
import kotlin.math.sin

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
        Row (modifier = modifier) {
            SpellathonGameScreen()
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
// Utility function to create a hexagonal path
fun createHexagonPath(size: Size): Path {
    return Path().apply {
// computer graphics and Android's coordinate system,
// the y-axis is inverted compared to traditional mathematical coordinate systems:
//        moveTo(size.width * 0.5f, 0f)
        moveTo(size.width * 0.25f, 0f) // left top corner
        lineTo(size.width * 0.75f, 0f) // right top corner
        lineTo(size.width, size.height * 0.5f) // right center corner
        lineTo(size.width * 0.75f, size.height) // right bottom corner
        lineTo(size.width * 0.25f, size.height) // left bottom corner
        lineTo(0f, size.height * 0.5f) // left center corner
        close() // connect back to first
    }
}

@Composable
fun SpellathonHexagonBoard(
    centerLetter: Char,
    edgeLetters: List<Char>,
    onLetterClicked: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    // Validate input
    require(edgeLetters.size == 6) { "Must provide exactly 6 edge letters" }

    // Hexagon shapes with outlines
    val outerHexagonShape = GenericShape { size, layoutDirection ->
        addPath(createHexagonPath(size))
    }

    val centerHexagonShape = GenericShape { size, layoutDirection ->
        addPath(createHexagonPath(size))
    }

    Box(
        modifier = modifier
            .size(300.dp)
            .border(2.dp, Color.Black, outerHexagonShape)
            .clip(outerHexagonShape),
        contentAlignment = Alignment.Center
    ) {
        // Canvas for drawing connections
        Canvas(modifier = Modifier.fillMaxSize()) {
            val outerSize = size
            val centerSize = Size(outerSize.width * 0.1f, outerSize.height * 0.1f)

            // Draw connections between outer and center hexagon corners
            val outerPath = createHexagonPath(outerSize)
            val centerPath = createHexagonPath(centerSize)

            // Get corner points of outer and center hexagons
            val outerCorners = listOf(
                Offset(outerSize.width * 0.25f, 0f),
                Offset(outerSize.width * 0.75f, 0f),
                Offset(outerSize.width, outerSize.height * 0.5f),
                Offset(outerSize.width * 0.75f, outerSize.height),
                Offset(outerSize.width * 0.25f, outerSize.height),
                Offset(0f, outerSize.height * 0.5f)
            )
            val centerCorners = listOf(
                Offset(centerSize.width * 0.25f, 0f),
                Offset(centerSize.width * 0.75f, 0f),
                Offset(centerSize.width, centerSize.height * 0.5f),
                Offset(centerSize.width * 0.75f, centerSize.height),
                Offset(centerSize.width * 0.25f, centerSize.height),
                Offset(0f, centerSize.height * 0.5f)
            )

            drawLine(color = Color.Black, start = outerCorners[0], end = outerCorners[3], strokeWidth=2f)
            drawLine(color = Color.Black, start = outerCorners[1], end = outerCorners[4], strokeWidth=2f)
            drawLine(color = Color.Black, start = outerCorners[2], end = outerCorners[5], strokeWidth=2f)

            // Draw connection lines
            // TODO this is not working because maths of inner vs outer corner is not exact ratio of size of components in dp
//            outerCorners.forEachIndexed { index, outerCorner ->
//                val centerCorner = centerCorners[index]
//                drawLine(
////                    color = Color.Black.copy(alpha = 0.3f),
//                    color = Color.Black,
//                    start = outerCorner,
//                    end = centerCorner,
//                    strokeWidth = 4f
//                )
//            }
        }

        // Center letter
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(2.dp, Color.Black, centerHexagonShape)
                .clip(centerHexagonShape)
                .zIndex(10f)
                .background(Color.DarkGray) // Distinct background using background colors or overlays to mask
                .clickable { onLetterClicked(centerLetter) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = centerLetter.toString(),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

        // Edge letters (positioned around the hexagon)
        val radius = 120.dp
        val angles = listOf(0f, 60f, 120f, 180f, 240f, 300f)
        val offset = 30.0f

        angles.forEachIndexed { index, angle ->
            val rads = Math.toRadians(angle.toDouble() + offset)
            val x = (radius.value * cos(rads)).dp
            val y = (radius.value * sin(rads)).dp

            Box(
                modifier = Modifier
                    .offset(x, y)
                    .size(60.dp)
//                    .border(2.dp, Color.Black, outerHexagonShape)
//                    .border(2.dp, Color.Black)
//                    .clip(outerHexagonShape)
                    .clickable { onLetterClicked(edgeLetters[index]) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = edgeLetters[index].toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
            }
        }
    }
}

// Example usage
@Composable
fun SpellathonGameScreen() {
    var selectedWord by remember { mutableStateOf("") }

    SpellathonHexagonBoard(
        centerLetter = 'A',
        edgeLetters = listOf('B', 'C', 'D', 'E', 'F', 'G'),
        onLetterClicked = { letter ->
            selectedWord += letter
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpellathonTheme {
        SpellathonLayout()
    }
}