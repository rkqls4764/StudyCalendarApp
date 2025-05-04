package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScheduleScreen(navController: NavHostController) {
    val name = "머신 러닝 공부"
    val description = "머신 러닝 기본 개념 공부\n파이썬으로 선형 회귀 구현"
    val date = "2020년 5월 13일 수요일"
    val time = "20:00 ~ 21:00"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "일정 조회하기", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.main_blue),
                    titleContentColor = Color.White
                ),
                // 뒤로 가기 버튼
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScheduleRow(label = "이름", value = name)
            ScheduleRow(label = "날짜", value = date)
            ScheduleRow(label = "시간", value = time)
            ScheduleRow(label = "설명", value = description)
        }
    }
}

@Composable
fun ScheduleRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = value,
                fontSize = 14.sp
            )
        }
    }
}
