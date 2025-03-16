package com.prem.scramble
//
//@HiltViewModel
//class LevelSelectionViewModel @Inject constructor(
//    private val gameDatabase: GameDatabase
//) : ViewModel() {
//    private val _levels = MutableStateFlow<List<Level>>(emptyList())
//    val levels: StateFlow<List<Level>> = _levels
//
//    init {
//        loadLevels()
//    }
//
//    private fun loadLevels() {
//        viewModelScope.launch {
//            val progress = gameDatabase.gameProgressDao().getAllProgress()
//            val levels = generateLevels().map { level ->
//                level.copy(
//                    completed = progress.find { it.levelId == level.id }?.completed ?: false
//                )
//            }
//            _levels.value = levels
//        }
//    }
//}
