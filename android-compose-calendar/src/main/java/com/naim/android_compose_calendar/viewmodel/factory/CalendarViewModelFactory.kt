package com.naim.android_compose_calendar.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naim.android_compose_calendar.attribute.CalendarAttribute
import com.naim.android_compose_calendar.viewmodel.CalendarViewModel
import java.lang.IllegalArgumentException

class CalendarViewModelFactory(private val calendarAttribute: CalendarAttribute = CalendarAttribute()) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(calendarAttribute) as T
        }
        throw IllegalArgumentException("Unknown class cast exception")
    }
}