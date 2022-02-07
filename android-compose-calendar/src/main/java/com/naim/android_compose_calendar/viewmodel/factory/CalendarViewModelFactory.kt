package com.naim.android_compose_calendar.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naim.android_compose_calendar.config.calendar_config.CalendarConfig
import com.naim.android_compose_calendar.viewmodel.CalendarViewModel

class CalendarViewModelFactory(private val calendarConfig: CalendarConfig = CalendarConfig()) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(calendarConfig) as T
        }
        throw IllegalArgumentException("Unknown class cast exception")
    }
}