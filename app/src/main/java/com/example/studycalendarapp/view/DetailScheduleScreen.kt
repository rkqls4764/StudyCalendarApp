package com.example.studycalendarapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.studycalendarapp.view.components.ConfirmDialog
import com.example.studycalendarapp.view.components.DetailSchedule
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.SubBlue
import com.example.studycalendarapp.viewmodel.DetailScheduleViewModel
import com.example.studycalendarapp.viewmodel.DetailStudyViewModel

/* 일정 조회 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScheduleScreen(navController: NavHostController, scheduleId: String) {
    val viewModel: DetailScheduleViewModel = viewModel()
    val schedule by viewModel.schedule.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchScheduleById(scheduleId)
    }

    if (showDialog) {
        ConfirmDialog(
            title = "일정 삭제",
            message = "정말로 이 일정을 삭제하시겠습니까?",
            onConfirm = {
                viewModel.deleteSchedule(scheduleId) {
                    navController.popBackStack()
                }
            },
            onDismiss = { showDialog = false }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "일정 조회하기",
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
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "뒤로 가기",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            schedule?.let {
                DetailSchedule(it)
            }

            Button( // 삭제 버튼
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBackDeep
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).height(50.dp)
            ) {
                Text(text = "삭제하기", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}