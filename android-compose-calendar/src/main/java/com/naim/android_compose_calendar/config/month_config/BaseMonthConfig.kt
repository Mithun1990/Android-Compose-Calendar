package com.naim.android_compose_calendar.config.month_config

import com.naim.android_compose_calendar.model.Month
import java.time.Year
import java.util.*

abstract class BaseMonthConfig {

    fun getLengthOfMonth(month: Int, year: Int): Int {
        return if (month == 1) 28 +
                (if (year % 4 == 0) 1 else 0) -
                (if (year % 100 == 0) if (year % 400 == 0) 0 else 1 else 0) else 31 - (month) % 7 % 2
    }

    fun getPrevMonthYear(month: Int, year: Int): Int {
        var year = year
        if (month == 1) {
            year -= 1
        }
        return year
    }

    fun getPrevMonth(month: Int): Int {
        var month = month
        month = if (month == 0) {
            12
        } else {
            month - 1
        }
        return month
    }

    fun getNextMonth(month: Int): Int {
        return if (month == 12) {
            0
        } else {
            month + 1
        }
    }

    fun getNextYear(month: Int, year: Int): Int {
        var year = year
        if (month == 12) {
            year += 1
        }
        return year
    }

    abstract fun configureMonth(date: Date, holidayList: List<Int>): Month

    abstract fun getMonthList(year: Int): List<Month>
}