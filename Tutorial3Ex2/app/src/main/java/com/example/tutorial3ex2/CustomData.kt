package com.example.tutorial3ex2

import java.io.Serializable

data class CustomData(
    val number1: Int,
    val number2: Int,
    val mode: Mode
) : Serializable {
    enum class Mode {
        ADD,
        MULTIPLY,
        SUBTRACT
    }
}

