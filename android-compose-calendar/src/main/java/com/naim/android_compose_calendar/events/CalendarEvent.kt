package com.naim.android_calendar_core.events

import java.util.*

sealed class CalendarEvent {
    object gotoPreviousYear : CalendarEvent()
    object gotoNextYear : CalendarEvent()
    object gotoPreviousMonth : CalendarEvent()
    object gotoNextMonth : CalendarEvent()
    data class onDateSelection(val date: Date) : CalendarEvent()
}