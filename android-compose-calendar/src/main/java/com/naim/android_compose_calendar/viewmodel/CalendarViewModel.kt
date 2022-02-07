package com.naim.android_compose_calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naim.android_compose_calendar.config.calendar_config.CalendarConfig
import com.naim.android_compose_calendar.extensions.*
import com.naim.android_compose_calendar.model.MonthItem
import com.naim.android_compose_calendar.state.CalendarUiState
import java.util.*

class CalendarViewModel(private val calendarConfig: CalendarConfig) : ViewModel() {
    private val _uiState: MutableLiveData<CalendarUiState> =
        MutableLiveData(CalendarUiState(calendarConfig))
    private val _monthItems: MutableLiveData<List<MonthItem>> = MutableLiveData(
        calendarConfig.monthConfig.getMonthItems(
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
            calendarConfig.monthConfig.getNextYear(
                uiState.value!!.selectedDate.getTheMonth(),
                uiState.value!!.selectedDate.getTheYear()
            ),
            calendarConfig.monthConfig.getNextMonth(uiState.value!!.selectedDate.getTheMonth()),
            1
        )
        if (calendarConfig.maxDate < nextDate)
            return
        _monthItems.value = calendarConfig.monthConfig.getMonthItems(nextDate, emptyList())
        selectedMonthTitle(nextDate)
    }

    fun gotoNextYear() {
        val nextYear = getFormattedDate(
            uiState.value!!.selectedDate.getTheYear() + 1,
            uiState.value!!.selectedDate.getTheMonth(),
            uiState.value!!.selectedDate.getTheDay()
        )
        if (calendarConfig.maxDate < nextYear)
            return
        _monthItems.value = calendarConfig.monthConfig.getMonthItems(nextYear, emptyList())
        selectedMonthTitle(nextYear)
    }

    fun gotoPreviousYear() {
        val nextYear = getFormattedDate(
            uiState.value!!.selectedDate.getTheYear() - 1,
            uiState.value!!.selectedDate.getTheMonth(),
            uiState.value!!.selectedDate.getTheDay()
        )
        if (calendarConfig.minDate > nextYear)
            return
        _monthItems.value = calendarConfig.monthConfig.getMonthItems(nextYear, emptyList())
        selectedMonthTitle(nextYear)
    }

    fun gotoPreviousMonth() {
        val prevDate = getFormattedDate(
            calendarConfig.monthConfig.getPrevMonthYear(
                uiState.value!!.selectedDate.getTheMonth(),
                uiState.value!!.selectedDate.getTheYear()
            ),
            calendarConfig.monthConfig.getPrevMonth(uiState.value!!.selectedDate.getTheMonth()),
            1
        )
        if (calendarConfig.minDate > prevDate)
            return
        _monthItems.value = calendarConfig.monthConfig.getMonthItems(prevDate, emptyList())
        selectedMonthTitle(prevDate)
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