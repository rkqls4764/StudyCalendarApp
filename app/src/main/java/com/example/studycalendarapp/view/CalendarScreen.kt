package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.ButtonBack
import com.example.studycalendarapp.view.components.ButtonBackDeep
import com.example.studycalendarapp.view.components.Calendar
import com.example.studycalendarapp.view.components.CalendarBottomNavigationBar
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.ScheduleItem
import com.example.studycalendarapp.view.components.SubBlue
import com.example.studycalendarapp.viewmodel.CalendarViewModel
import com.example.studycalendarapp.viewmodel.JoinStudyViewModel

/* 일정 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavHostController, studyId: String) {
    val viewModel: CalendarViewModel = viewModel()
    val studyName by viewModel.studyName.collectAsState() // 스터디 이름
    val scheduleList by viewModel.scheduleList.collectAsState() // 일정 목록

    LaunchedEffect(Unit) {
        viewModel.fetchStudyById(studyId)
        viewModel.fetchScheduleList(studyId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = studyName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainBlue
                ),
                navigationIcon = {  // 뒤로 가기 버튼
                    IconButton(onClick = { navController.popBackStack() }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "뒤로 가기",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton( // 일정 생성 버튼
                onClick = {
                    navController.navigate("addSchedule/${studyId}")
                },
                containerColor = SubBlue,
                shape = RoundedCornerShape(50)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        bottomBar = {
            CalendarBottomNavigationBar(
                navController,
                onItemClick = { id ->
                    when (id) {
                        "calendar" -> navController.navigate("calendar/${studyId}")
                        "chating" -> navController.navigate("chating")
                        "detailStudy" -> navController.navigate("detailStudy/${studyId}")
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Calendar()  // 캘린더

            Divider(color = ButtonBackDeep, thickness = 1.dp, modifier = Modifier.padding(top = 20.dp))

            LazyColumn( // 일정 목록
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(scheduleList) { schedule ->
                    ScheduleItem(schedule, onButtonClick = {
                        navController.navigate("detailStudy/studyId") // 일정 조회 화면으로 이동
                    })
                }
            }
        }
    }
}