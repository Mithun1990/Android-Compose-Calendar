package com.naim.android_compose_calendar.config.month_config

import com.naim.android_compose_calendar.config.week_config.IWeekConfig
import com.naim.android_compose_calendar.extensions.getMonthName
import com.naim.android_compose_calendar.extensions.getTheDay
import com.naim.android_compose_calendar.extensions.getTheMonth
import com.naim.android_compose_calendar.extensions.getTheYear
import com.naim.android_compose_calendar.model.Month
import com.naim.android_compose_calendar.model.MonthItem
import java.util.*

class MonthConfigImpl(private val weekConfig: IWeekConfig) : BaseMonthConfig(), IMonthConfig {
    override fun getMonthItems(
        date: Date,
        holidayList: List<Int>,
        listOfDisableDate: List<Date>
    ): List<MonthItem> {
        val monthItems = mutableListOf<MonthItem>()
        val month = configureMonth(date, holidayList)
        monthItems.addAll(addPreviousMonthExtraDay(month))
        monthItems.addAll(addCurrentMonthDay(month))
        monthItems.addAll(addNextMonthExtraDay(month))
        println("Month ${monthItems}")
        return monthItems
    }

    private fun addPreviousMonthExtraDay(month: Month): List<MonthItem> {
        val previousMonthExtraItems = mutableListOf<MonthItem>()
        val pervMonthExtraDays = month.firstDayOfMonth - 1;
        for (i in 0 until pervMonthExtraDays) {
//            println("Month ${month.yearOfMonth} ${month.monthId - 1} ${month.previousMonthDays - i}")
//            println("Month ${ getCalendarInstance(
//                month.yearOfMonth,
//                month.monthId - 1,
//                month.previousMonthDays - i
//            ).time}")
            previousMonthExtraItems.add(
                MonthItem.MonthItemBuilder(
                    getCalendarInstance(
                        month.yearOfMonth,
                        month.monthId - 1,
                        month.previousMonthDays - i
                    ).time
                ).build()
            )
        }
//        println("Month ${previousMonthExtraItems}")
        return previousMonthExtraItems.reversed()
    }

    private fun addCurrentMonthDay(month: Month): List<MonthItem> {
        val currentMonthItems = mutableListOf<MonthItem>()
        val pervMonthExtraDays = month.firstDayOfMonth - 1;
        for (i in 0 until month.noOfDays) {
            val weekId: Int =
                if ((i + 1 + pervMonthExtraDays) % weekConfig.getWeekItems().size == 0)
                    weekConfig.getWeekItems().size else (i + 1 + pervMonthExtraDays) % weekConfig.getWeekItems().size
            val weekDay: Int = weekConfig.getMapOfWeekIdVsDay()[weekId] ?: 1
            currentMonthItems.add(
                MonthItem.MonthItemBuilder(
                    getCalendarInstance(
                        month.yearOfMonth,
                        month.monthId,
                        i + 1
                    ).time
                ).isSelected(
                    i + 1 == month.selectedDate.getTheDay() &&
                            month.monthId == month.selectedDate.getTheMonth() &&
                            month.yearOfMonth == month.selectedDate.getTheYear()
                ).isSelectable(true)
                    .isHoliday(month.listOfHolidays.contains(weekDay)).build()
            )
        }
        return currentMonthItems
    }

    private fun addNextMonthExtraDay(month: Month): List<MonthItem> {
        val nextMonthExtraItems = mutableListOf<MonthItem>()
        val daysAfterFirstWeek: Int =
            month.noOfDays - (weekConfig.getWeekItems().size - (month.firstDayOfMonth - 1))
        val extraDays: Int = daysAfterFirstWeek % weekConfig.getWeekItems().size
        for (i in 0 until weekConfig.getWeekItems().size - extraDays) {
            nextMonthExtraItems.add(
                MonthItem.MonthItemBuilder(
                    getCalendarInstance(
                        month.yearOfMonth,
                        month.monthId + 1,
                        i + 1
                    ).time
                ).build()
            )
        }
        return nextMonthExtraItems
    }

    private fun getCalendarInstance(year: Int, month: Int, day: Int): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
    }

    override fun configureMonth(date: Date, holidayList: List<Int>): Month {
        val _currentMonthYear = date.getTheYear()
        val _prevMonthYear = getPrevMonthYear(date.getTheMonth(), date.getTheYear())
        val _prevMonth = getPrevMonth(date.getTheMonth())
        val monthLength = getLengthOfMonth(date.getTheMonth(), date.getTheYear())
        val _prevMonthLength = getLengthOfMonth(_prevMonth, _prevMonthYear)
        val currentMonthFirstDate =
            getCalendarInstance(_currentMonthYear, date.getTheMonth(), 1).get(Calendar.DAY_OF_WEEK)
        println("Month ${currentMonthFirstDate}")
        println("Month ${weekConfig.getMapOfWeekDayVsId()[currentMonthFirstDate] ?: 1}")
        return Month(
            date.getTheMonth(),
            date.getMonthName(),
            monthLength,
            date.getTheYear(),
            _prevMonthLength,
            weekConfig.getMapOfWeekDayVsId()[currentMonthFirstDate] ?: 1,
            date,
            holidayList
        )
    }

    override fun getMonthList(year: Int): List<Month> {
        val monthList = mutableListOf<Month>()
        for (month in 0..11) {
            monthList.add(configureMonth(getCalendarInstance(year, month, 1).time, emptyList()))
        }
        println("Previous Year ${monthList}")
        return monthList
    }
}