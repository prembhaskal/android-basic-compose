package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeQuadrant()
                }
            }
        }
    }
}

@Composable
fun ComposeQuadrant(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            CellContent(
                "Text Composable",
                "Displays text and follows the recommended Material Design guidelines.",
                0xFFEADDFF,
                modifier = Modifier.weight(1f),
            )
            CellContent(
                "Image composable",
                "Creates a composable that lays out and draws a given Painter class object.",
                0xFFD0BCFF,
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier.weight(1f)
        ) {

            CellContent(
                "Row composable",
                "A layout composable that places its children in a horizontal sequence.",
                0xFFB69DF8,
                modifier = Modifier.weight(1f),
            )
            CellContent(
                "Column composable",
                "A layout composable that places its children in a vertical sequence.",
                0xFFF6EDFF,
                modifier = Modifier.weight(1f),
            )
        }
    }

}


@Composable
fun CellContent(heading: String, content: String, colorCode: Long, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color(colorCode))
    ) {
        CellText(heading, content, modifier)
    }
}

@Composable
fun CellText(heading: String, content: String, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = heading,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = content,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            textAlign = TextAlign.Justify,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ComposeQuadrantPreview() {
    ComposeQuadrantTheme {
        ComposeQuadrant()
    }
}