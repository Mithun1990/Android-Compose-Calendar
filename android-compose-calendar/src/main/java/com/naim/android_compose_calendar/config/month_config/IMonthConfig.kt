package com.naim.android_compose_calendar.config.month_config

import com.naim.android_compose_calendar.model.Month
import com.naim.android_compose_calendar.model.MonthItem
import java.util.*

interface IMonthConfig {
    fun getMonthItems(date: Date, holidayList: List<Int>): List<MonthItem>
}