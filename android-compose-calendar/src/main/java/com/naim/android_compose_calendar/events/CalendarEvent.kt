package com.naim.android_compose_calendar.events

import java.util.*

sealed class CalendarEvent {
    data class previousYearSelection(val date: Date) : CalendarEvent()
    data class nextYearSelection(val date: Date) : CalendarEvent()
    data class previousMonthSelection(val date: Date) : CalendarEvent()
    data class nextMonthSelection(val date: Date) : CalendarEvent()
    data class dateSelectionSelection(val date: Date) : CalendarEvent()
}