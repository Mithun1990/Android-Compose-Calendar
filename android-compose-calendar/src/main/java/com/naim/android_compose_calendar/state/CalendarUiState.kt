package com.naim.android_compose_calendar.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.*

class CalendarUiState(val date: Date) {
    var selectedDate by mutableStateOf(date)
}