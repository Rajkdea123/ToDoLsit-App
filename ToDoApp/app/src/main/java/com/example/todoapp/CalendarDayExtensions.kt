package com.example.todoapp

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

fun CalendarDay.timeInMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.clearExcess()
    calendar.set(this.year, this.month - 1, this.day)
    return calendar.timeInMillis
}

fun Calendar.clearExcess() {
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MILLISECOND)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.HOUR)
}