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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.InputField
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.view.components.SubBlue
import com.example.studycalendarapp.viewmodel.AddStudyViewModel

/* 스터디 생성 화면 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudyScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: AddStudyViewModel = viewModel()
    val study by viewModel.study.collectAsState() // 스터디 정보
    var tagInput by remember { mutableStateOf("") } // 태그 입력 값

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "스터디 생성하기",
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
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField("이름", study.name) { new ->
                viewModel.updateStudy { it.copy(name = new) }
            }

            InputField("날짜", study.date) { new ->
                viewModel.updateStudy { it.copy(date = new) }
            }

            InputField("시간", study.time) { new ->
                viewModel.updateStudy { it.copy(time = new) }
            }

            InputField("설명", study.description) { new ->
                viewModel.updateStudy { it.copy(description = new) }
            }

            InputField("태그", tagInput) { new ->
                tagInput = new
                val tagList = new.trim().split("\\s+".toRegex())
                viewModel.updateStudy { it.copy(tag = tagList) }
            }

            InputField("방식", study.method) { new ->
                viewModel.updateStudy { it.copy(method = new) }
            }

            Spacer(modifier = Modifier.padding(bottom = 100.dp))

            Button( // 생성 버튼
                 onClick = {
                     viewModel.saveStudy(
                         onSuccess = {
                             Toast.makeText(context, "스터디 생성 성공!", Toast.LENGTH_SHORT).show()
                             navController.popBackStack()   // 뒤로 가기
                         },
                         onFailure = {
                             Toast.makeText(context, "스터디 생성 실패", Toast.LENGTH_SHORT).show()
                         }
                     )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SubBlue
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).height(50.dp)
            ) {
                Text(text = "생성하기", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}