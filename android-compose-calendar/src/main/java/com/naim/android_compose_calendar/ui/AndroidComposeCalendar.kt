package com.naim.android_compose_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.naim.android_compose_calendar.R
import com.naim.android_calendar_core.config.calendar_config.CalendarConfig
import com.naim.android_calendar_core.config.week_config.IWeekConfig
import com.naim.android_calendar_core.extensions.formattedDate
import com.naim.android_calendar_core.extensions.getTheDay
import com.naim.android_calendar_core.model.MonthItem
import com.naim.android_calendar_core.state.CalendarUiState
import com.naim.android_calendar_core.state.CalendarUiView
import com.naim.android_calendar_core.viewmodel.CalendarViewModel
import com.naim.android_compose_calendar.DragEvent
import com.naim.android_compose_calendar.dispatch_provider.IDispatchProvider
import com.naim.android_compose_calendar.events.CalendarEvent
import java.util.*
import androidx.compose.runtime.remember as remember

@OptIn(ExperimentalUnitApi::class)
@Composable
@Deprecated(
    message = "This function is deprecated",
    replaceWith = ReplaceWith("AndroidComposableCalendar")
)
fun AndroidComposeCalendar(
    viewModel: CalendarViewModel,
    calendarConfig: CalendarConfig,
    onDateSelected: (date: Date) -> Unit
) {
    calendarDayViewUI(viewModel, calendarConfig, {}, onDateSelected)
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AndroidComposableCalendar(
    viewModel: CalendarViewModel,
    calendarConfig: CalendarConfig,
    onCalendarEvent: (calendarEvent: CalendarEvent) -> Unit
) {
    val calendarUiState by viewModel.uiState.observeAsState()
    when (calendarUiState?.currentCalendarUiView) {
        CalendarUiView.DAY_VIEW -> {
            calendarDayViewUI(viewModel, calendarConfig, onCalendarEvent)
        }
        CalendarUiView.MONTH_VIEW -> {
            calendarMonthViewUI(viewModel, calendarConfig, onCalendarEvent)
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun calendarMonthViewUI(
    viewModel: CalendarViewModel,
    calendarConfig: CalendarConfig,
    onCalendarEvent: (calendarEvent: CalendarEvent) -> Unit
) {
    Column {
        LazyColumn {
            gridView(viewModel.monthList.value!!, 3) { item, itemIndex ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .weight(0.1f, fill = true)
                            .height(50.dp)
                            .background(calendarConfig.monthListBgColor)
                            .clickable {
                                viewModel.setCalendarUiView(CalendarUiView.DAY_VIEW)
                                viewModel.setMonth(item.selectedDate)
                                viewModel.selectedDate(item.selectedDate)
                                onCalendarEvent.invoke(CalendarEvent.onMonthSelection(item))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.monthTitle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            style = calendarConfig.monthListItemTextStyle ?: TextStyle(
                                color = calendarConfig.monthListItemTextColor,
                                textAlign = TextAlign.Center,
                                fontSize = TextUnit(16f, TextUnitType.Sp)
                            )
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun calendarDayViewUI(
    viewModel: CalendarViewModel,
    calendarConfig: CalendarConfig,
    onCalendarEvent: (calendarEvent: CalendarEvent) -> Unit,
    onDateSelected: (date: Date) -> Unit = {}
) {
    var dragValue = DragEvent()
    Column(Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, dragAmount ->
                dragValue = DragEvent(dragAmount.x, dragAmount.y)
            },
            onDragEnd = {
                when {
                    dragValue.xAmount < 0 -> {
                        viewModel.nextMonth()
                    }
                    dragValue.xAmount > 0 -> {
                        viewModel.gotoPreviousMonth()
                    }
                }
            },
        )
    }) {
        calendarDayView(viewModel, calendarConfig, onCalendarEvent)
    }
}

@ExperimentalUnitApi
@Composable
fun calendarDayView(
    viewModel: CalendarViewModel,
    calendarConfig: CalendarConfig,
    onCalendarEvent: (calendarEvent: CalendarEvent) -> Unit,
    onDateSelected: (date: Date) -> Unit = {}
) {
    var state = viewModel.uiState.observeAsState()
    var stateMonth = viewModel.monthItems.observeAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(
            onClick = {
                viewModel.gotoPreviousYear()
                onCalendarEvent.invoke(CalendarEvent.previousYearSelection(state.value!!.currentYear))
            },
            enabled = calendarConfig.isYearChangedEnabled
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_left_arriw_year),
                contentDescription = "Previous Year",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
                    .weight(.1f, true),
            )
        }
        IconButton(
            onClick = {
                viewModel.gotoPreviousMonth()
                onCalendarEvent.invoke(CalendarEvent.previousMonthSelection(state.value!!.currentMonth))
            },
            enabled = calendarConfig.isMonthChangeEnabled
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_left_arriw_month),
                contentDescription = "Previous Month",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
                    .weight(.1f, true)
            )
        }
        Text(
            text = state.value!!.selectedMonth,
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentWidth()
                .clickable {
                    viewModel.getMonthList()
                    viewModel.setCalendarUiView(CalendarUiView.MONTH_VIEW)
                }
                .weight(.5f, true),
            style = calendarConfig.monthTitleTextStyle ?: TextStyle(
                color = Color.LightGray, fontSize = TextUnit(
                    16f, TextUnitType.Sp
                )
            )
        )
        IconButton(
            onClick = {
                viewModel.nextMonth()
                onCalendarEvent.invoke(CalendarEvent.nextMonthSelection(state.value!!.currentMonth))
            },
            enabled = calendarConfig.isMonthChangeEnabled
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_right_arriw_month),
                contentDescription = "Next Month",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
                    .weight(.1f, true)
            )
        }
        IconButton(
            onClick = {
                viewModel.gotoNextYear()
                onCalendarEvent.invoke(CalendarEvent.nextYearSelection(state.value!!.currentYear))
            },
            enabled = calendarConfig.isYearChangedEnabled
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_right_arriw_year),
                contentDescription = "Next Year",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
                    .weight(.1f, true)
            )
        }
    }

//        viewModel.selectedDate(Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 9) }.time)
    CalendarWeekRow(calendarConfig.weekConfig)
    CalendarMonthColumn(
        calendarConfig,
        viewModel,
        onCalendarEvent,
        state.value!!,
        stateMonth.value!!
    )
}


@Composable
fun CalendarWeekRow(weekConfigImpl: IWeekConfig) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (item in weekConfigImpl.getWeekItems()) {
                Row(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { },
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(text = item.weekTitle, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CalendarMonthColumn(
    calendarConfig: CalendarConfig,
    viewModel: CalendarViewModel,
    onCalendarEvent: (calendarEvent: CalendarEvent) -> Unit,
    state: CalendarUiState,
    monthItems: List<MonthItem>,
    onDateSelected: (date: Date) -> Unit = {}
) {
//    println("Month ${state!!.selectedDate}")
    Column {
        LazyColumn {
            gridView(monthItems, gridColumn = 7) { item, itemIndex ->
                Row(
                    modifier = Modifier
                        .size(40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = item.date!!.getTheDay().toString(),
                        color = when {
                            !item.isSelectable -> {
                                calendarConfig.disableDateTextColor
                            }
                            state.selectedDate.formattedDate() == item.date!!.formattedDate() -> {
                                calendarConfig.selectedDateTextColor
                            }
                            state.currentDate.formattedDate() == item.date!!.formattedDate() -> {
                                calendarConfig.currentDateTextColor
                            }
                            else -> {
                                calendarConfig.normalDateTextColor
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    viewModel.selectedDate(item.date!!)
                                    onCalendarEvent.invoke(CalendarEvent.dateSelectionSelection(item.date!!))
                                    onDateSelected.invoke(item.date!!)
                                },
                                enabled = item.isSelectable
                            )
                            .background(
                                when {
                                    state.selectedDate.formattedDate() == item.date!!.formattedDate() -> {
                                        calendarConfig.selectedDateBgColor
                                    }
                                    state.currentDate.formattedDate() == item.date!!.formattedDate() -> {
                                        calendarConfig.currentDateBgColor
                                    }
                                    else -> {
                                        Color.Transparent
                                    }
                                }, shape = CircleShape
                            )
                            .padding(10.dp)
                            .layout { measurable, constraints ->
                                // Measure the composable
                                val placeable = measurable.measure(constraints)

                                //get the current max dimension to assign width=height
                                val currentHeight = placeable.height
                                var heightCircle = currentHeight
                                if (placeable.width > heightCircle)
                                    heightCircle = placeable.width

                                //assign the dimension and the center position
                                layout(heightCircle, heightCircle) {
                                    // Where the composable gets placed
                                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                                }
                            }
                            .clip(CircleShape),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

fun <T> LazyListScope.gridView(
    data: List<T>,
    gridColumn: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T, Int) -> Unit
) {
    val rows = if (data.count() == 0) 0 else 1 + (data.count() - 1) / gridColumn
    items(rows) { rowIndex ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (columnIndex in 0 until gridColumn) {
                val itemIndex = rowIndex * gridColumn + columnIndex
                if (itemIndex < data.count()) {
                    Box(
                        modifier = Modifier
                            .weight(0.1f, fill = true),
                        contentAlignment = Alignment.Center
                    ) {
                        itemContent.invoke(this, data[itemIndex], itemIndex)
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}

class TestModifier(modifier: Modifier, val string: (String) -> Unit) : Modifier.Element {
    init {

        var dragValue: DragEvent = DragEvent()
        string.invoke("Value ${dragValue}")
        modifier.pointerInput(Unit) {
            detectDragGestures(onDrag = { change, dragAmount ->
                dragValue = DragEvent(dragAmount.x, dragAmount.y)
            }, onDragEnd = {
                string.invoke("Value ${dragValue}")
                println("Value ${dragValue}")
            })
        }
    }
}