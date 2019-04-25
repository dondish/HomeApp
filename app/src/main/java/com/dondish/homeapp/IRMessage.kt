package com.dondish.homeapp

import kotlin.math.roundToInt

class IRMessage(val frequency: Int, val codes: IntArray) {
    companion object {
        fun fromPronto(a: Array<String>): IRMessage {
            val frequency: Int = (1000000 / (a[1].toInt(16) * 0.241246)).roundToInt() // Get the frequency
            val seq = a[2].toInt(16) * 2
            val rep = a[3].toInt(16) * 2
            val message = a.sliceArray(4 until 4+(seq + rep)).map { it.toInt(16) * (1000000 / frequency)}.toIntArray()
            return IRMessage(frequency, message)
        }
    }
}

