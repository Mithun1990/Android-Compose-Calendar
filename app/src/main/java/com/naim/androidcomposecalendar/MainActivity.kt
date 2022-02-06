package com.naim.androidcomposecalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.ui.AndroidComposeCalendar
import com.naim.android_compose_calendar.viewmodel.CalendarViewModel
import com.naim.android_compose_calendar.viewmodel.factory.CalendarViewModelFactory
import com.naim.androidcomposecalendar.ui.theme.AndroidComposeCalendarTheme

class MainActivity : ComponentActivity() {
    private val calendarViewModel: CalendarViewModel by viewModels { CalendarViewModelFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeCalendar(calendarViewModel)
//            AndroidComposeCalendarTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
//
//            }
        }

        println(
            "Month Data: ${
                MonthConfigImpl(IWeekConfigImpl()).getMonthList(2022)
            }"
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidComposeCalendarTheme {
        Greeting("Android")
    }
}