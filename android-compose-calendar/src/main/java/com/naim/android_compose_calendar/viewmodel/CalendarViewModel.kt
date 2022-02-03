package com.naim.android_compose_calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naim.android_compose_calendar.state.CalendarUiState
import java.util.*

class CalendarViewModel : ViewModel() {
    private val _uiState: MutableLiveData<CalendarUiState> = MutableLiveData()
    private val selectedDate = MutableLiveData(Date())
    val ui: LiveData<Date> = selectedDate
    val uiState: LiveData<CalendarUiState>
        get() = _uiState

    fun selectedDate(value: Date) {
        println("Month $value")
        selectedDate.value = value
        _uiState.value?.apply {
            this.selectedDate = value
            _uiState.value = this
        }
    }
}