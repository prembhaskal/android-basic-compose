package com.prem.spellathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            Column (
                modifier = modifier.fillMaxWidth().weight(1f).requiredSize(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painter = painterResource(R.drawable.spell_1),
                    contentDescription = "Spell puzzle",
                    modifier = modifier.requiredSize(150.dp))
            }

            Column(
                modifier = modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text (
                    text = "Rules",
                    modifier = modifier,
                )
            }
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