package com.naim.android_compose_calendar.dispatch_provider

import com.naim.android_compose_calendar.events.CalendarEvent

interface IDispatchProvider {
    fun onEvent(event: CalendarEvent)
}