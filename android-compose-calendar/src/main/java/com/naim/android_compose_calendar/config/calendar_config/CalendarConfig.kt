package com.naim.android_compose_calendar.config.calendar_config

import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfig
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.util.Constants
import java.util.*

class CalendarConfig {
    val weekConfig: IWeekConfig by lazy { IWeekConfigImpl() }
    val monthConfig: MonthConfigImpl by lazy { MonthConfigImpl(weekConfig) }
    val PREFER_DATE_FORMAT = Constants.PREFER_DATE_FORMAT
    val MONTH_NAME_DATE_FORMAT = Constants.MONTH_NAME_DATE_FORMAT
    val CALENDAR_MONTH_TITLE_DATE_FORMAT = Constants.CALENDAR_MONTH_TITLE_DATE_FORMAT
    var date: Date = Date()
    val currentDate: Date = date
    var minDate: Date = Calendar.getInstance().apply {
        set(Calendar.YEAR, 1900)
        set(Calendar.MONTH, 0)
        set(Calendar.DAY_OF_MONTH, 1)
    }.time
    var maxDate: Date = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2100)
        set(Calendar.MONTH, 11)
        set(Calendar.DAY_OF_MONTH, 31)
    }.time

}