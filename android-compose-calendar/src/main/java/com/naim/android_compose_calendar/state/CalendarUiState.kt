package com.naim.android_compose_calendar.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.naim.android_compose_calendar.config.calendar_config.CalendarConfig
import com.naim.android_compose_calendar.extensions.getCalendarMonthTitle
import com.naim.android_compose_calendar.extensions.getMonthName
import com.naim.android_compose_calendar.extensions.getTheMonth
import java.util.*

class CalendarUiState(calendarConfig: CalendarConfig) {
    var selectedDate by mutableStateOf(calendarConfig.date)
    var selectedMonth by mutableStateOf(calendarConfig.date.getCalendarMonthTitle())
    var currentDate by mutableStateOf(calendarConfig.currentDate)
}