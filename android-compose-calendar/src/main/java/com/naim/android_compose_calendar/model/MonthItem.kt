package com.naim.android_compose_calendar.model

import com.naim.android_compose_calendar.extensions.getMonthName
import com.naim.android_compose_calendar.extensions.getTheDay
import java.util.*

class MonthItem constructor(monthItemBuilder: MonthItemBuilder) {
    var date: Date? = null
    var isSelected: Boolean = false
    var isSelectable = false
    var isHoliday = false

    init {
        this.date = monthItemBuilder.date
        this.isSelected = isSelected
        this.isSelectable = isSelectable
        this.isHoliday = isHoliday
    }


    class MonthItemBuilder constructor(date: Date) {
        var date: Date? = null
        var isSelected: Boolean = false
        var isSelectable = false
        var isHoliday = false

        init {
            this.date = date
        }

        fun isSelected(isSelected: Boolean): MonthItemBuilder {
            this.isSelected = isSelected
            return this
        }

        fun isSelectable(isSelectable: Boolean): MonthItemBuilder {
            this.isSelectable = isSelectable
            return this
        }

        fun isHoliday(isHoliday: Boolean): MonthItemBuilder {
            this.isHoliday = isHoliday;
            return this
        }

        fun build(): MonthItem {
            return MonthItem(this)
        }
    }

    override fun toString(): String {
        return "MonthItem(date=${date!!.getMonthName()}, ${date!!.getTheDay()})"
    }
}