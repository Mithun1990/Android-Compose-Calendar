package com.naim.android_compose_calendar.ui

import android.provider.Contacts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naim.android_compose_calendar.R
import com.naim.android_compose_calendar.config.month_config.MonthConfigImpl
import com.naim.android_compose_calendar.config.week_config.IWeekConfigImpl
import com.naim.android_compose_calendar.dispatch_provider.IDispatchProvider
import com.naim.android_compose_calendar.events.CalendarEvent
import com.naim.android_compose_calendar.extensions.formattedDate
import com.naim.android_compose_calendar.extensions.getTheDay
import com.naim.android_compose_calendar.model.MonthItem
import java.time.Year
import java.util.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AndroidComposeCalendar(uiViewModel: UIViewModel) {
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
            Text(
                text = "January, 2021",
                Modifier
                    .padding(10.dp)
                    .clickable {
                        uiViewModel.getNewMonth(
                            Calendar
                                .getInstance()
                                .apply {
                                    set(Calendar.YEAR, 2022)
                                    set(Calendar.MONTH, 2)
                                    set(Calendar.DAY_OF_MONTH, 1)
                                }.time, emptyList()
                        )
                    },
                style = TextStyle(
                    color = Color.DarkGray, fontSize = TextUnit(
                        16f, TextUnitType.Sp
                    )
                )
            )
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

        }

    }
    CalendarWeekRow(weekImpl)
    CalendarMonthColumn(monthConfigImpl = monthConfig, uiViewModel)
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
fun CalendarMonthColumn(monthConfigImpl: MonthConfigImpl, uiViewModel: UIViewModel) {
    uiViewModel.getNewMonth(
        Calendar
            .getInstance()
            .apply {
                set(Calendar.YEAR, 2022)
                set(Calendar.MONTH, 0)
                set(Calendar.DAY_OF_MONTH, 1)
            }.time, emptyList()
    )
    val monthItems = uiViewModel.monthItems.observeAsState()
//    var selectedDate by remember {
//        mutableStateOf(Date())
//    }
    var selectedDate = uiViewModel.ud.observeAsState()
    LazyColumn {
        gridView(
            monthItems.value as List<MonthItem>,
            gridColumn = 7,
            selectedDate.value?.d!!.value,
            { uiViewModel.setVal(it) }) { item ->
            Text(
                text = item.date!!.getTheDay().toString(),
                textAlign = TextAlign.Center
            )
        }
    }
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

sealed class UIState {
    class DateSelection(var selectedDate: Date) : UIState()
}


data class UIS(val d: MutableState<Date> = mutableStateOf(Date()))

class UIViewModel : ViewModel() {

    private var _ud = MutableLiveData(UIS())
    val ud = _ud

    private var _ui = MutableLiveData(UIState.DateSelection(Date()))
    private var uiUIState = MutableLiveData(
        MonthConfigImpl(IWeekConfigImpl()).getMonthItems(Date(), emptyList())
    )
    val ui = _ui
    val monthItems = uiUIState
    fun setUI(value: Date) {
        _ui.value = UIState.DateSelection(value)
    }

    fun setVal(value: Date) {
        _ud.value?.apply {
            this.d.value = value
            _ud.value = this
        }
    }


    fun getNewMonth(date: Date, list: List<Int>) {
        uiUIState.value = MonthConfigImpl(IWeekConfigImpl()).getMonthItems(date, emptyList())
        setVal(date)
    }
}