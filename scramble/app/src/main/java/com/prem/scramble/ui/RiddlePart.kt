package com.prem.scramble.ui

sealed class RiddlePart {
    data class Text(val content: String) : RiddlePart()
    data class Blank(val length: Int) : RiddlePart()
}