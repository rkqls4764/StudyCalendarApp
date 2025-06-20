package com.example.studycalendarapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.ButtonBack
import com.example.studycalendarapp.view.components.ButtonBackDeep
import com.example.studycalendarapp.view.components.ChatHistory
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.StartChat
import com.example.studycalendarapp.view.components.StudyBottomNavigationBar
import com.example.studycalendarapp.view.components.SubBlue
import com.example.studycalendarapp.viewmodel.ChatingViewModel

/* AI 챗봇 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatingScreen(navController: NavHostController) {
    val viewModel: ChatingViewModel = viewModel()
    val chatHistory by viewModel.chatHistory.collectAsState() // 채팅 내역
    val isInputEnabled by viewModel.isInputEnabled.collectAsState() // 입력 가능 여부
    
    var inputText by remember { mutableStateOf("") }    // 입력 내용
    var selectedFunction by remember { mutableStateOf("") } // 선택한 챗봇 기능
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "AI 챗봇과 대화하기",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ChatHistory(
                Modifier.weight(1f),
                chatHistory,
                onRecommendStudyClick = {
                    viewModel.addAIMessage("관심사를 입력해주세요.")
                    selectedFunction = "study"
                },
                onRecommendLearningClick = {
                    viewModel.addAIMessage("추천 받을 학습 주제를 입력해주세요.")
                    selectedFunction = "learning"
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputText,
                    enabled = isInputEnabled,
                    onValueChange = { inputText = it },
                    placeholder = { Text(text = if (isInputEnabled) "메세지를 작성해주세요" else "기능을 선택해주세요") },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = ButtonBack,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = {
                        if (selectedFunction == "study") {
                            viewModel.recommendStudy(inputText)
                        }
                        else if (selectedFunction == "learning") {
                            viewModel.recommendLearningContent(inputText)
                        }

                        selectedFunction = ""
                        inputText = ""
                    },
                    enabled = isInputEnabled,
                    modifier = Modifier.background(
                        color = SubBlue,
                        shape = RoundedCornerShape(10.dp)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "전송",
                        tint = Color.White
                    )
                }
            }
        }
    }
}