package com.naim.android_compose_calendar.attribute

import androidx.compose.runtime.remember
import com.naim.android_compose_calendar.config.month_config.IMonthConfig
import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfig
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.util.Constants

class CalendarAttribute {
    val weekConfig: IWeekConfig by lazy { IWeekConfigImpl() }
    val monthConfig: MonthConfigImpl by lazy { MonthConfigImpl(weekConfig) }
    val PREFER_DATE_FORMAT = Constants.PREFER_DATE_FORMAT
    val MONTH_NAME_DATE_FORMAT = Constants.MONTH_NAME_DATE_FORMAT
    val CALENDAR_MONTH_TITLE_DATE_FORMAT = Constants.CALENDAR_MONTH_TITLE_DATE_FORMAT
}