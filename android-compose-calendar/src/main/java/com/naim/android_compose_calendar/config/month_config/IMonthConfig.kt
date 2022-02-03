package com.naim.android_compose_calendar.config.month_config

import com.naim.android_compose_calendar.model.MonthItem
import java.util.*

interface IMonthConfig {
    fun getMonthItems(
        date: Date,
        holidayList: List<Int>,
        listOfDisableDate: List<Date> = emptyList()
    ): List<MonthItem>
}