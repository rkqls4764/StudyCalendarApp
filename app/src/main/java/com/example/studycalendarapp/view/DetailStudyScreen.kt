package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.studycalendarapp.view.components.CalendarBottomNavigationBar
import com.example.studycalendarapp.view.components.DetailStudy
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.viewmodel.CalendarViewModel
import com.example.studycalendarapp.viewmodel.DetailStudyViewModel

/* 스터디 정보 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailStudyScreen(navController: NavHostController, studyId: String) {
    val viewModel: DetailStudyViewModel = viewModel()
    val study by viewModel.study.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchStudyById(studyId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "스터디 정보",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MainBlue
                ),
                navigationIcon = {  // 뒤로 가기 버튼
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "뒤로 가기",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            CalendarBottomNavigationBar(
                navController,
                onItemClick = { id ->
                    when (id) {
                        "calendar" -> navController.navigate("calendar/${studyId}") { popUpTo(navController.currentDestination?.route ?: return@navigate) { inclusive = true } }
                        "chating" -> navController.navigate("chating") { popUpTo(navController.currentDestination?.route ?: return@navigate) { inclusive = true } }
                        "detailStudy" -> navController.navigate("detailStudy/${studyId}") { popUpTo(navController.currentDestination?.route ?: return@navigate) { inclusive = true } }
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
            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            study?.let { DetailStudy(it) }  // 스터디 정보
        }
    }
}