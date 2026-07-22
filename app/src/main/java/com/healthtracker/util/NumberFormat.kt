package com.healthtracker.util

fun Double.toCleanString(): String {
    return if (this == this.toInt().toDouble()) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}