package com.example.tasks.time

import java.text.SimpleDateFormat
import java.util.*

const val DATE = "HH.mm / dd/MM/yy"

object TimeFuns {
    fun stringToMillis(time: String) : Long {
        var long = 0L
        val date = SimpleDateFormat(DATE, Locale.UK).parse(time)
        date?.let {
            long = date.time
        }
        return long
    }

    fun millisToString(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(DATE, Locale.UK)
        return format.format(date)
    }
}