package com.naim.android_compose_calendar.config.week_config

import com.naim.android_compose_calendar.model.WeekItem

interface IWeekConfig {
    fun getWeekItems(): List<WeekItem>
    fun getMapOfWeekIdVsDay(): Map<Int, Int>
    fun getMapOfWeekDayVsId(): Map<Int, Int>
}