package com.dondish.homeapp

class IRMessage(a: Array<String>) {
    // Get the frequency
    val frequency: Int = (1000000 / (a[1].toInt(16) * 0.241246)).toInt()
    val message = a.sliceArray(4 until a.size).map { Math.ceil(it.toInt(16) * 26.27272727272727).toInt() }.toIntArray()
}