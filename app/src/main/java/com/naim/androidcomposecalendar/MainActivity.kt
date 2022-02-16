package com.naim.androidcomposecalendar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.naim.android_calendar_core.config.calendar_config.CalendarConfig
import com.naim.android_calendar_core.viewmodel.CalendarViewModel
import com.naim.android_calendar_core.viewmodel.factory.CalendarViewModelFactory
import com.naim.android_compose_calendar.events.CalendarEvent
import com.naim.android_compose_calendar.ui.AndroidComposableCalendar
import com.naim.android_compose_calendar.ui.AndroidComposeCalendar
import java.util.*

class MainActivity : ComponentActivity() {
    private val calendarConfig: CalendarConfig = CalendarConfig().apply {
        date = Date()
        currentDateTextColor = Color(0xFF03DAC5)
        disableDateTextColor = Color(0xFFCCCCCC)
        monthListBgColor = Color(0xFF87584E)
        monthListItemTextColor = Color(0xFFFFFFee)
    }
    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            calendarConfig
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposableCalendar(calendarViewModel, calendarConfig,
                onCalendarEvent = {
                    when (it) {
                        is CalendarEvent.dateSelectionSelection -> {
                            println("Selected date ${it.date}")
                        }
                        is CalendarEvent.nextMonthSelection -> {
                            println("Selected date ${it.month}")
                        }
                        is CalendarEvent.onMonthSelection -> {
                            println("Selected Month ${it.month}")
                        }
                        is CalendarEvent.nextYearSelection -> {
                            println("Selected Year ${it.year}")
                        }
                    }
                })
        }
    }
}