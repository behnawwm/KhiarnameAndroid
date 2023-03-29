package com.example.khiarname.util

import com.example.khiarname.data.Portal

fun List<Portal>.formatToText(): String {
    return joinToString(separator = " - ") { "${it.start},${it.end}" }
}