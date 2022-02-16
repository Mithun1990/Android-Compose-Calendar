package com.naim.android_compose_calendar.events

import com.naim.android_calendar_core.model.Month
import com.naim.android_calendar_core.model.Year
import java.util.*

sealed class CalendarEvent {
    data class previousYearSelection(val year: Year) : CalendarEvent()
    data class nextYearSelection(val year: Year) : CalendarEvent()
    data class previousMonthSelection(val month: Month) : CalendarEvent()
    data class nextMonthSelection(val month: Month) : CalendarEvent()
    data class dateSelectionSelection(val date: Date) : CalendarEvent()
    data class onMonthSelection(val month: Month) : CalendarEvent()
}