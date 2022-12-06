package com.binatebits.food_reccomendation_system.util

fun Float.roundTo(n : Int) : Float {
    return "%.${n}f".format(this).toFloat()
}
fun Double.roundTo(n : Int) : Double {
    return "%.${n}f".format(this).toDouble()
}