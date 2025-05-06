package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.DetailStudy
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.SubBlue

/* 스터디 참여 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinStudyScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "스터디 참여하기",
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
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DetailStudy()

            Spacer(modifier = Modifier.padding(bottom = 100.dp))

            Button( // 참여 버튼
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SubBlue
                )
            ) {
                Text(text = "참여하기", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}