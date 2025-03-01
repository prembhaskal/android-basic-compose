package com.prem.scramble

import android.app.GameState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prem.scramble.database.GameDatabase
import com.prem.scramble.database.GameProgressEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameDatabase: GameDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val levelId: Int = savedStateHandle["levelId"] ?: 1

    private val _gameState = MutableStateFlow<GameState>(GameState.Loading)
    val gameState: StateFlow<GameState> = _gameState

    private val _currentInputs = MutableStateFlow<List<String>>(List(4) { "" })
    val currentInputs: StateFlow<List<String>> = _currentInputs

    private var currentLevel: Level? = null

    init {
        loadGame()
    }

    private fun loadGame() {
        viewModelScope.launch {
            val levels = generateLevels()
            val level = levels.find { it.id == levelId } ?: levels[0]

            // Load saved progress
            val progress = gameDatabase.gameProgressDao().getProgressForLevel(levelId)
            if (progress != null) {
                _currentInputs.value = progress.solvedWords.split(",")
            }

            _gameState.value = GameState.Playing(level)
            currentLevel = level
        }
    }

    private suspend fun saveProgress() {
        currentLevel?.let { level ->
            val progress = GameProgressEntity(
                levelId = level.id,
                completed = true,
                solvedWords = _currentInputs.value.joinToString(",")
            )
            gameDatabase.gameProgressDao().saveProgress(progress)
        }
    }

    private fun generateLevels(): List<Level> {
        return listOf(
            Level(
                id = 1,
                words = listOf(
                    Word("HAPPY", "PAHPY"),
                    Word("CLOUD", "DUCLO"),
                    Word("SMILE", "LISME"),
                    Word("BRAIN", "NBRAI")
                )
            ),
            Level(
                id = 2,
                words = listOf(
                    Word("SHAPE", "HEAPS"),
                    Word("LIGHT", "THLIG"),
                    Word("DREAM", "MREAD"),
                    Word("WATER", "TWARE")
                )
            ),
            Level(
                id = 3,
                words = listOf(
                    Word("DANCE", "CANDE"),
                    Word("MUSIC", "CUSIM"),
                    Word("SOUND", "DUSNO"),
                    Word("RHYTHM", "THRYM")
                )
            ),
            Level(
                id = 4,
                words = listOf(
                    Word("EARTH", "THEAR"),
                    Word("SPACE", "CESPA"),
                    Word("STARS", "RASST"),
                    Word("MOON", "NOOM")
                )
            ),
            Level(
                id = 5,
                words = listOf(
                    Word("COLOR", "ROLOC"),
                    Word("PAINT", "NIPAT"),
                    Word("BRUSH", "SHRUB"),
                    Word("STYLE", "LYSTY")
                )
            )
        )
    }
}
