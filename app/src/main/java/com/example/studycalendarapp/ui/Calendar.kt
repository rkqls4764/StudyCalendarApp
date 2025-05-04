package com.example.studycalendarapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment

/* 캘린더 */
@Composable
fun Calendar() {
    val dateTodayState = remember { mutableStateOf(LocalDate.now()) }   // 현재 날짜
    val selectedDateState = remember { mutableStateOf(LocalDate.now()) }    // 선택한 날짜

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val startDate = remember { mutableStateOf(selectedDateState.value!!.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))) }    // 선택한 월의 시작 날짜 ("yyyyMMdd")
        val endDate = remember { mutableStateOf(selectedDateState.value!!.withDayOfMonth(selectedDateState.value!!.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"))) }  // 선택한 월의 마지막 날짜 ("yyyyMMdd")

        MonthMove(  // 월 이동 버튼
            dateTodayState = dateTodayState,
            onMonthChanged = { newMonth ->
                startDate.value = newMonth.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                endDate.value = newMonth.withDayOfMonth(newMonth.lengthOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            }
        )
        DayOfWeekBar()  // 요일 출력
        MonthBlock( // 한 달 출력
            currentDateState = dateTodayState
        )
    }
}

/* 캘린더 한 달 출력 */
@Composable
fun MonthBlock(
    currentDateState: MutableState<LocalDate>   // 현재 날짜
) {
    val currentDate = currentDateState.value
    val lastDay = currentDate.lengthOfMonth()   // 현재 달의 마지막 날짜
    val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek.value  // 현재 달의 시작 요일
    val days = IntRange(1, lastDay).toList()    // 현재 달의 전체 날짜

    Column(
        modifier = Modifier
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            //처음 날짜 시작 요일까지 빈 박스 생성
            for (i in 0 until firstDayOfWeek) {
                item {
                    Box { }
                }
            }

            itemsIndexed(days) { index, day ->
                val date = currentDate.withDayOfMonth(day)  // currentDate에서 일자를 day로 변경
                Column {
                    Divider(modifier = Modifier.padding(horizontal = 8.dp))

                    Box(
                        modifier = Modifier,
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
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                val previousMonth = currentMonth.minusMonths(1)
                val newDate = adjustDayOfMonth(previousMonth, currentMonth.dayOfMonth)
                dateTodayState.value = newDate
                onMonthChanged(newDate)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent // 버튼 배경색 제거
            )
        ) {
            Text(text = "<", color = Color.Black, fontSize = 18.sp)
        }

        Text(
            text = currentMonth.format(dateFormatter),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                val nextMonth = currentMonth.plusMonths(1)
                val newDate = adjustDayOfMonth(nextMonth, currentMonth.dayOfMonth)
                dateTodayState.value = newDate
                onMonthChanged(newDate)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent // 버튼 배경색 제거
            )
        ) {
            Text(text = ">", color = Color.Black, fontSize = 18.sp)
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
    Column (
        modifier = Modifier.padding(10.dp)
    ) {
        //날짜 출력
        Text(
            text = date.dayOfMonth.toString(),
            fontSize = 15.sp,
            color = if (date == LocalDate.now()) Color.White else Color.Black, //오늘 날짜는 다른 색상으로 표시
            modifier = Modifier
                .background(
                    color = if (date == LocalDate.now()) Color.Black else Color.Transparent, //오늘 날짜는 다른 색상으로 표시
                    shape = CircleShape
                )
                .padding(3.dp, 1.dp, 3.dp, 1.dp)
        )
    }
}