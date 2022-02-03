package com.naim.android_compose_calendar.extensions

import com.naim.android_compose_calendar.util.Constants
import java.text.SimpleDateFormat
import java.util.*

fun Date.getTheDay(): Int {
    return getInstance().get(Calendar.DAY_OF_MONTH)
}

fun Date.getTheMonth(): Int {
    return getInstance().get(Calendar.MONTH)
}

fun Date.getTheYear(): Int {
    return getInstance().get(Calendar.YEAR)
}

fun Date.getWeekDay(): Int {
    return getInstance().get(Calendar.DAY_OF_WEEK)
}

fun Date.formattedDate(): Date {
    val sdf = SimpleDateFormat(Constants.PREFER_DATE_FORMAT, Locale.ENGLISH)
    val tempDate = sdf.format(getInstance().time).toString()
    return sdf.parse(tempDate)
        ?: this
}

fun Date.getMonthName(): String {
    val sdf = SimpleDateFormat(Constants.MONTH_NAME_DATE_FORMAT, Locale.ENGLISH)
    return sdf.format(getInstance().time)
}

fun Date.getInstance(): Calendar {
    return Calendar.getInstance().apply {
        time = this@getInstance
    }
}