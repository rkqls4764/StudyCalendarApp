package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.studycalendarapp.view.components.Calendar
import com.example.studycalendarapp.view.components.CalendarBottomNavigationBar
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.ScheduleItem
import com.example.studycalendarapp.view.components.SubBlue

/* 일정 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController) {
    val name = "Data Science 부트캠프"
    val scheduleList = listOf("일정1", "일정2", "일정3")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainBlue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(   // 일정 생성 버튼
                onClick = {
                    navController.navigate("addSchedule")
                },
                containerColor = SubBlue
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        bottomBar = {
            CalendarBottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Calendar()  // 캘린더

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp))

            LazyColumn( // 일정 목록
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(scheduleList) { schedule ->
                    ScheduleItem(schedule, navController)
                }
            }
        }
    }
}