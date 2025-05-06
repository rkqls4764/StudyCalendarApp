package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.StudyBottomNavigationBar
import com.example.studycalendarapp.view.components.StudyItem
import com.example.studycalendarapp.viewmodel.JoiningStudyViewModel
import com.example.studycalendarapp.viewmodel.LoginViewModel

/* 가입한 스터디 목록 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoiningStudyScreen(navController: NavHostController) {
    val viewModel: JoiningStudyViewModel = viewModel()
    val studyList by viewModel.studyList.collectAsState()   // 가입 중인 스터디 목록

    LaunchedEffect(Unit) {
        viewModel.fetchJoiningStudyList()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "가입 중인 스터디",
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
        bottomBar = {
            StudyBottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(studyList) { study ->
                    StudyItem(study, "일정 관리", onButtonClick = {
                        navController.navigate("calendar/${study.id}")  // 일정 관리 화면으로 이동
                    })
                }
            }
        }
    }
}