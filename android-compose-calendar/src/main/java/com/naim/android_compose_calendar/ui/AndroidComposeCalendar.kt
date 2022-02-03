package com.naim.android_compose_calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import java.util.*

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun AndroidComposeCalendar() {
    val weekImpl = IWeekConfigImpl()
    val monthConfig = MonthConfigImpl(weekImpl)
//    Column {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 10.dp, end = 10.dp, top = 20.dp),
//            verticalAlignment = Alignment.Top,
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            IconButton(onClick = { monthConfig.getMonthList(2021) }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.icon_left_arriw_year),
//                    contentDescription = "Previous Year",
//                    Modifier
//                        .padding(10.dp)
//                        .size(24.dp),
//                )
//            }
//            Icon(
//                painter = painterResource(id = R.drawable.icon_left_arriw_month),
//                contentDescription = "Previous Month",
//                Modifier
//                    .padding(10.dp)
//                    .size(24.dp)
//            )
//            Text(
//                text = "January, 2021",
//                Modifier.padding(10.dp),
//                style = TextStyle(
//                    color = Color.DarkGray, fontSize = TextUnit(
//                        16f, TextUnitType.Sp
//                    )
//                )
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.icon_right_arriw_month),
//                contentDescription = "Next Year",
//                Modifier
//                    .padding(10.dp)
//                    .size(24.dp)
//            )
//            Icon(
//                painter = painterResource(id = R.drawable.icon_right_arriw_year),
//                contentDescription = "Next Month",
//                Modifier
//                    .padding(10.dp)
//                    .size(24.dp)
//            )
//
//        }
//
//    }
    CalendarWeekRow(weekImpl)
    CalendarMonthColumn(monthConfigImpl = monthConfig)
}

@Composable
fun CalendarWeekRow(weekConfigImpl: IWeekConfigImpl) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (item in weekConfigImpl.getWeekItems()) {
            Box(
                modifier = Modifier.weight(0.1f, fill = true),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.weekTitle, modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
fun CalendarMonthColumn(monthConfigImpl: MonthConfigImpl) {
    val monthItems = monthConfigImpl.getMonthItems(Date(), emptyList())
    var selectedDate by remember {
        mutableStateOf(Date())
    }
    LazyColumn {

        gridView(monthItems, gridColumn = 7, selectedDate, { selectedDate = it }) { item ->
            Text(
                text = item.date!!.getTheDay().toString(),
                textAlign = TextAlign.Center
            )
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

fun <T> LazyListScope.gridView(
    data: List<T>,
    gridColumn: Int,
    selectedDate: Date,
    onSelectDate: (Date) -> Unit,
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
                            .clickable {
                                println("Month $selectedDate ${(data[itemIndex] as MonthItem).date}")
                                onSelectDate.invoke((data[itemIndex] as MonthItem).date!!)
                            }
                            .weight(0.1f, fill = true)
                            .background(if (selectedDate.formattedDate() == (data[itemIndex] as MonthItem).date!!.formattedDate()) Color.Green else Color.Transparent),
                        contentAlignment = Alignment.Center,
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