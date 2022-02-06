package com.naim.android_compose_calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naim.android_compose_calendar.attribute.CalendarAttribute
import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.extensions.formattedDate
import com.naim.android_compose_calendar.extensions.getCalendarMonthTitle
import com.naim.android_compose_calendar.extensions.getTheMonth
import com.naim.android_compose_calendar.extensions.getTheYear
import com.naim.android_compose_calendar.model.MonthItem
import com.naim.android_compose_calendar.state.CalendarUiState
import java.util.*

class CalendarViewModel(private val calendarAttribute: CalendarAttribute) : ViewModel() {
    private val _uiState: MutableLiveData<CalendarUiState> =
        MutableLiveData(CalendarUiState(Date()))
    private val _monthItems: MutableLiveData<List<MonthItem>> = MutableLiveData(
        calendarAttribute.monthConfig.getMonthItems(
            Date(), emptyList()
        )
    )
    private val selectedDate = MutableLiveData(Date())
    val ui: LiveData<Date> = selectedDate
    val uiState: LiveData<CalendarUiState>
        get() = _uiState
    val monthItems: LiveData<List<MonthItem>>
        get() = _monthItems

    fun nextMonth() {
        val nextDate = getFormattedDate(
            calendarAttribute.monthConfig.getNextYear(
                uiState.value!!.selectedDate.getTheMonth(),
                uiState.value!!.selectedDate.getTheYear()
            ),
            calendarAttribute.monthConfig.getNextMonth(uiState.value!!.selectedDate.getTheMonth()),
            1
        )
        _monthItems.value = calendarAttribute.monthConfig.getMonthItems(nextDate, emptyList())
        selectedMonthTitle(nextDate)
    }

    fun gotoPreviousMonth() {
        val nextDate = getFormattedDate(
            calendarAttribute.monthConfig.getPrevMonthYear(
                uiState.value!!.selectedDate.getTheMonth(),
                uiState.value!!.selectedDate.getTheYear()
            ),
            calendarAttribute.monthConfig.getPrevMonth(uiState.value!!.selectedDate.getTheMonth()),
            1
        )
        println("Month $nextDate")
        _monthItems.value = calendarAttribute.monthConfig.getMonthItems(nextDate, emptyList())
        selectedMonthTitle(nextDate)
    }

    fun selectedDate(value: Date) {
        println("Month $value")
        _uiState.value?.apply {
            this.selectedDate = value
            _uiState.value = this
        }
    }

    fun selectedMonthTitle(value: Date) {
        _uiState.value?.apply {
            this.selectedDate = value
            this.selectedMonth = value.formattedDate().getCalendarMonthTitle()
            _uiState.value = this
        }
    }

    private fun getFormattedDate(year: Int, month: Int, day: Int): Date {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }.time
    }
}