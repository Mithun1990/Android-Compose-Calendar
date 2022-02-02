package com.naim.android_compose_calendar.config.week_config

import com.naim.android_compose_calendar.model.WeekItem

class IWeekConfigImpl : IWeekConfig {
    override fun getWeekItems(): List<WeekItem> {
        return listOf(
            WeekItem("Su", 1, 1),
            WeekItem("Mo", 2, 2),
            WeekItem("Tu", 3, 3),
            WeekItem("We", 4, 4),
            WeekItem("Th", 5, 5),
            WeekItem("Fr", 6, 6),
            WeekItem("Sa", 7, 7),
        )
    }

    override fun getMapOfWeekIdVsDay(): Map<Int, Int> {
        return getWeekItems().associate { it.weekId to it.weekDay }
    }

    override fun getMapOfWeekDayVsId(): Map<Int, Int> {
        return getWeekItems().associate { it.weekDay to it.weekId }
    }
}