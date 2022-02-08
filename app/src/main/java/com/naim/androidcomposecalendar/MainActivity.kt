package com.naim.androidcomposecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.naim.android_calendar_core.config.calendar_config.CalendarConfig
import com.naim.android_calendar_core.viewmodel.CalendarViewModel
import com.naim.android_calendar_core.viewmodel.factory.CalendarViewModelFactory
import com.naim.android_compose_calendar.ui.AndroidComposeCalendar

class MainActivity : ComponentActivity() {
    private val calendarConfig: CalendarConfig = CalendarConfig()
    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory(
            calendarConfig
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeCalendar(calendarViewModel, calendarConfig,
                onDateSelected = { date ->
                    println("Selected date $date")
                })

        }
    }
}