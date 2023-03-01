package com.example.tasks.time

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi

class Picker(
    private val ctx: Context,
    private val text: TextView,
    private val time: Long?
    ) : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private var nowYear: Int = 0
    private var nowMonth: Int = 0
    private var nowDay: Int = 0
    private var nowHour: Int = 0
    private var nowMin: Int = 0
    private var selYear: Int = 0 // sel = selected
    private var selMonth: Int = 0
    private var selDay: Int = 0
    private var selHour: Int = 0
    private var selMin: String = "0"

    fun pickTime() {
        getTime(time)
        DatePickerDialog(ctx, this, nowYear, nowMonth, nowDay).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        selYear = year
        selMonth = month + 1
        selDay = day
        TimePickerDialog(ctx, this, nowHour, nowMin, true).show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onTimeSet(view: TimePicker?, hour: Int, min: Int) {
        selHour = hour
        selMin = if (min < 10) "0$min" else min.toString()
        val timeString = "$selHour.$selMin / $selDay/$selMonth/$selYear" // "HH.mm / dd/MM/yy"
        text.text = timeString
    }

    private fun getTime(time: Long?) {
        val cal = Calendar.getInstance()
        time?.let { cal.timeInMillis = time }
        nowYear = cal[Calendar.YEAR]
        nowMonth = cal[Calendar.MONTH]
        nowDay = cal[Calendar.DAY_OF_MONTH]
        nowHour = cal[Calendar.HOUR_OF_DAY]
    }
}