package com.example.studycalendarapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.ButtonBack
import com.example.studycalendarapp.view.components.ButtonBackDeep
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.StudyBottomNavigationBar
import com.example.studycalendarapp.view.components.StudyItem
import com.example.studycalendarapp.view.components.SubBlue
import com.example.studycalendarapp.viewmodel.JoiningStudyViewModel
import com.example.studycalendarapp.viewmodel.RecruitViewModel

/* 스터디 모집 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecruitScreen(navController: NavHostController) {
    val viewModel: RecruitViewModel = viewModel()
    val searchText by viewModel.searchText.collectAsState() // 검색어
    val filteredList by viewModel.filteredStudyList.collectAsState() // 필터링된 스터디 목록

    LaunchedEffect(Unit) {
        viewModel.fetchStudyList()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "모집 중인 스터디",
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
            FloatingActionButton(   // 스터디 생성 버튼
                onClick = {
                    navController.navigate("addStudy")
                },
                containerColor = SubBlue
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        bottomBar = {
            StudyBottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) { // 검색 바
                TextField( // 검색어 입력 필드
                    value = searchText,
                    onValueChange = { viewModel.updateSearchText(it) },
                    placeholder = {
                        Text(
                            text = "검색어를 입력하세요",
                            fontSize = 16.sp
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "검색 버튼"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            // 스터디 목록
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(filteredList) { study ->
                    StudyItem(study, "참여하기", onButtonClick = {
                        navController.navigate("joinStudy/${study.id}")  // 스터디 참여 화면으로 이동
                    })
                }
            }
        }
    }
}