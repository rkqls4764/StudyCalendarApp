package com.example.studycalendarapp.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.studycalendarapp.viewmodel.CalendarViewModel

/* 캘린더 */
@Composable
fun Calendar(viewModel: CalendarViewModel) {
    val dateTodayState = remember { mutableStateOf(LocalDate.now()) } // 현재 날짜
    val selectedDate by viewModel.selectedDate.collectAsState() //선택한 날짜
    val selectedDateState = remember { mutableStateOf(selectedDate) } // 선택한 날짜 상태

    LaunchedEffect(selectedDate) {
        selectedDateState.value = selectedDate
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val startDate = remember { mutableStateOf(selectedDate.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))) }    // 선택한 월의 시작 날짜 ("yyyyMMdd")
        val endDate = remember { mutableStateOf(selectedDate.withDayOfMonth(selectedDate.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"))) }  // 선택한 월의 마지막 날짜 ("yyyyMMdd")

        MonthMove(  // 월 이동 버튼
            dateTodayState = dateTodayState,
            onMonthChanged = { newMonth ->
                // 월 이동 시 이동한 월의 1일을 선택된 날짜로 설정
                val firstDayOfMonth = newMonth.withDayOfMonth(1)
                viewModel.updateSelectedDate(firstDayOfMonth)

                startDate.value = newMonth.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                endDate.value = newMonth.withDayOfMonth(newMonth.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            }
        )

        DayOfWeekBar() // 요일 출력

        MonthBlock(dateTodayState) // 한 달 날짜 출력
    }
}

/* 한 달 날짜 출력 */
@Composable
fun MonthBlock(
    currentDateState: MutableState<LocalDate>   // 현재 날짜
) {
    val currentDate = currentDateState.value
    val lastDay = currentDate.lengthOfMonth()   // 현재 달의 마지막 날짜
    val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek.value  // 현재 달의 시작 요일
    val days = IntRange(1, lastDay).toList()    // 현재 달의 전체 날짜

    Column {
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            //처음 날짜 시작 요일까지 빈 박스 생성
            for (i in 0 until firstDayOfWeek) {
                item {
                    Box { }
                }
            }

            itemsIndexed(days) { _, day ->
                val date = currentDate.withDayOfMonth(day)  // currentDate에서 일자를 day로 변경
                Column {
                    Divider(modifier = Modifier.padding(horizontal = 8.dp))

                    Box(
                        modifier = Modifier
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DayBlock(date)
                    }
                }
            }
        }
    }
}

/* 년,월 이동 버튼 */
@Composable
fun MonthMove(
    dateTodayState: MutableState<LocalDate>,
    onMonthChanged: (LocalDate) -> Unit
) {
    val currentMonth = dateTodayState.value
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                val previousMonth = currentMonth.minusMonths(1)
                val newDate = adjustDayOfMonth(previousMonth, currentMonth.dayOfMonth)
                dateTodayState.value = newDate
                onMonthChanged(newDate)
            }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "이전 월 버튼")
        }

        Text(
            text = currentMonth.format(dateFormatter),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = {
                val nextMonth = currentMonth.plusMonths(1)
                val newDate = adjustDayOfMonth(nextMonth, currentMonth.dayOfMonth)
                dateTodayState.value = newDate
                onMonthChanged(newDate)
            }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "다음 월 버튼")
        }
    }
}

/* 날짜 유효성 검증 및 조정 함수 */
fun adjustDayOfMonth(moveMonth: LocalDate, lastDayOfMonth: Int): LocalDate { //이동할 월, 현재 달의 마지막 일
    return if (lastDayOfMonth > moveMonth.lengthOfMonth()) { //주어진 lastDayOfMonth가 해당 월의 최대 일수를 초과하는지 확인
        moveMonth.withDayOfMonth(moveMonth.lengthOfMonth()) //초과하면 해당 월의 마지막 날로 설정
    } else {
        moveMonth.withDayOfMonth(lastDayOfMonth)
    }
}

/* 요일 바 */
@Composable
fun DayOfWeekBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
            Text(text = day, textAlign = TextAlign.Center, fontSize = 14.sp)
        }
    }
}

/* 날짜 블럭 */
@Composable
fun DayBlock(
    date: LocalDate
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text( //날짜 출력
            text = date.dayOfMonth.toString(),
            fontSize = 15.sp,
            fontWeight = if (date == LocalDate.now()) FontWeight.Bold else FontWeight.Normal,
            color = if (date == LocalDate.now()) SubBlue else Color.Black, //오늘 날짜는 다른 색상으로 표시
            modifier = Modifier.fillMaxWidth().padding(start = 2.dp)
        )
    }
}