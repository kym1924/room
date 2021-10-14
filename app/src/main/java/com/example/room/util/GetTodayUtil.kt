package com.example.room.util

import java.text.SimpleDateFormat
import java.util.Locale

fun getToday(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(System.currentTimeMillis())
