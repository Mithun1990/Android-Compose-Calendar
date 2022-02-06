package com.naim.android_compose_calendar.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.naim.android_compose_calendar.extensions.getCalendarMonthTitle
import com.naim.android_compose_calendar.extensions.getMonthName
import com.naim.android_compose_calendar.extensions.getTheMonth
import java.util.*

class CalendarUiState(val date: Date) {
    var selectedDate by mutableStateOf(date)
    var selectedMonth by mutableStateOf(date.getCalendarMonthTitle())
}