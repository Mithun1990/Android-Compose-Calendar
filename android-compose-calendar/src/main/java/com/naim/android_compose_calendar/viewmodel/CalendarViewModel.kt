package com.naim.android_compose_calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.extensions.getTheMonth
import com.naim.android_compose_calendar.extensions.getTheYear
import com.naim.android_compose_calendar.model.MonthItem
import com.naim.android_compose_calendar.state.CalendarUiState
import java.util.*

class CalendarViewModel : ViewModel() {
    private val _uiState: MutableLiveData<CalendarUiState> =
        MutableLiveData(CalendarUiState(Date()))
    private val _monthItems: MutableLiveData<List<MonthItem>> = MutableLiveData(
        MonthConfigImpl(IWeekConfigImpl()).getMonthItems(
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
        val m = MonthConfigImpl(IWeekConfigImpl())
        _monthItems.value = MonthConfigImpl(IWeekConfigImpl()).getMonthItems(
            Calendar.getInstance().apply {
                set(
                    Calendar.YEAR,
                    m.getNextYear(
                        uiState.value!!.selectedDate.getTheMonth(),
                        uiState.value!!.selectedDate.getTheYear()
                    )
                )
                set(Calendar.MONTH, m.getNextMonth(uiState.value!!.selectedDate.getTheMonth()))
                set(Calendar.DAY_OF_MONTH, 1)
            }.time, emptyList()
        )
        selectedDate(Calendar.getInstance().apply {
            set(
                Calendar.YEAR,
                m.getNextYear(
                    uiState.value!!.selectedDate.getTheMonth(),
                    uiState.value!!.selectedDate.getTheYear()
                )
            )
            set(Calendar.MONTH, m.getNextMonth(uiState.value!!.selectedDate.getTheMonth()))
            set(Calendar.DAY_OF_MONTH, 1)
        }.time)
    }

    fun selectedDate(value: Date) {
        println("Month $value")
        _uiState.value?.apply {
            this.selectedDate = value
            _uiState.value = this
        }
    }
}