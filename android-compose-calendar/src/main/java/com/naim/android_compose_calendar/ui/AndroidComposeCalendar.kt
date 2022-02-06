package com.naim.android_compose_calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.naim.android_compose_calendar.R
import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.extensions.formattedDate
import com.naim.android_compose_calendar.extensions.getTheDay
import com.naim.android_compose_calendar.model.MonthItem
import com.naim.android_compose_calendar.state.CalendarUiState
import com.naim.android_compose_calendar.viewmodel.CalendarViewModel
import java.util.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AndroidComposeCalendar(viewModel: CalendarViewModel) {

    val weekImpl = IWeekConfigImpl()
    val monthConfig = MonthConfigImpl(weekImpl)
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 20.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { monthConfig.getMonthList(2021) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_left_arriw_year),
                    contentDescription = "Previous Year",
                    Modifier
                        .padding(10.dp)
                        .size(24.dp),
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.icon_left_arriw_month),
                contentDescription = "Previous Month",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
            )
            Text(
                text = "January, 2021",
                Modifier.padding(10.dp),
                style = TextStyle(
                    color = Color.DarkGray, fontSize = TextUnit(
                        16f, TextUnitType.Sp
                    )
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_right_arriw_month),
                contentDescription = "Next Year",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_right_arriw_year),
                contentDescription = "Next Month",
                Modifier
                    .padding(10.dp)
                    .size(24.dp)
            )

        }
        var state = viewModel.uiState.observeAsState()
        viewModel.selectedDate(Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 9) }.time)
        CalendarWeekRow(weekImpl)
        CalendarMonthColumn(monthConfigImpl = monthConfig, viewModel, state.value!!)
    }

}

@Composable
fun CalendarWeekRow(weekConfigImpl: IWeekConfigImpl) {

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
    monthConfigImpl: MonthConfigImpl,
    viewModel: CalendarViewModel,
    state: CalendarUiState
) {
    val monthItems = monthConfigImpl.getMonthItems(Date(), emptyList())

    println("Month ${state!!.selectedDate}")
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
                        color = if (!item.isSelectable) Color.Yellow else Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { viewModel.selectedDate(item.date!!) },
                                enabled = item.isSelectable
                            )
                            .background(
                                if (state.selectedDate.formattedDate() == item.date!!.formattedDate())
                                    Color.Magenta else Color.Transparent, shape = CircleShape
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
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }


//    val noOfRow = monthItems.size / 7;
//    for (i in 0 until noOfRow) {
//        println("Month ${i * 7} ${i * 7 + 6}")
//        CalendarMonthRow(
//            monthItems.subList(
//                i * 7,
//                if (i * 7 + 7 > monthItems.size - 1) monthItems.size else i * 7 + 7
//            )
//        )
//    }
}

@Composable
fun CalendarMonthRow(splitMonthItems: List<MonthItem>) {
    println("Month ${splitMonthItems}")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (item in splitMonthItems) {
            Box(
                modifier = Modifier.weight(0.1f, fill = true),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.date!!.getTheDay().toString(),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

fun LazyListScope.gridItem() {

}

fun <T> LazyListScope.calendarGridView(
    data: List<T>,
    gridColumn: Int,
    selectedValue: T,
    onSelected: (T) -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit
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
                            .clickable { println("Month $itemIndex") }
                            .weight(0.1f, fill = true),
                        contentAlignment = Alignment.Center
                    ) {
                        itemContent.invoke(this, data[itemIndex])
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f, fill = true))
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