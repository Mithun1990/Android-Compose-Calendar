package com.naim.android_compose_calendar.model

import com.naim.android_compose_calendar.extensions.getMonthName
import java.util.*

class MonthItem constructor(monthItemBuilder: MonthItemBuilder) {
    var date: Date? = null
    var isSelected: Boolean = false
    var isSelectable = false
    var isHoliday = false

    init {
        this.date = monthItemBuilder.date
        this.isSelected = monthItemBuilder.isSelected
        this.isSelectable = monthItemBuilder.isSelectable
        this.isHoliday = monthItemBuilder.isHoliday
    }


    class MonthItemBuilder constructor(date: Date) {
        var date: Date? = null
        var isSelected: Boolean = false
        var isSelectable: Boolean = false
        var isHoliday: Boolean = false

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
        return "MonthItem(date=${date!!.getMonthName()}, ${isSelectable})"
    }
}