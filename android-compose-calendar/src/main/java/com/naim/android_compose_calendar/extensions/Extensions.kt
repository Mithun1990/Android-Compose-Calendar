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

fun Date.formattedDate(format: String = Constants.PREFER_DATE_FORMAT): Date {
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.parse(sdf.format(getInstance().time).toString())
        ?: this
}

fun Date.getMonthName(format: String = Constants.MONTH_NAME_DATE_FORMAT): String {
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(getInstance().time)
}

fun Date.getCalendarMonthTitle(format: String = Constants.CALENDAR_MONTH_TITLE_DATE_FORMAT): String {
    val sdf = SimpleDateFormat(format, Locale.ENGLISH)
    return sdf.format(getInstance().time)
}

fun Date.getInstance(): Calendar {
    return Calendar.getInstance().apply {
        time = this@getInstance
    }
}