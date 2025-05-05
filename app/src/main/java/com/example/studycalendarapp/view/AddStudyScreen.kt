package com.example.studycalendarapp.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudyScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: AddStudyViewModel = viewModel()
    val study by viewModel.study.collectAsState()

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
                // 뒤로 가기 버튼
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

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            InputField("날짜", study.date) { new ->
                viewModel.updateStudy { it.copy(date = new) }
            }

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            InputField("시간", study.time) { new ->
                viewModel.updateStudy { it.copy(time = new) }
            }

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            InputField("설명", study.description) { new ->
                viewModel.updateStudy { it.copy(description = new) }
            }

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            InputField("태그", study.tag) { new ->
                viewModel.updateStudy { it.copy(tag = new) }
            }

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            InputField("방식", study.method) { new ->
                viewModel.updateStudy { it.copy(method = new) }
            }

            Spacer(modifier = Modifier.padding(bottom = 100.dp))

            Button( // 생성 버튼
                 onClick = {
                     viewModel.saveStudy(
                         onSuccess = {
                             Toast.makeText(context, "스터디 생성 성공!", Toast.LENGTH_SHORT).show()
                                     /* 뒤로 가기 */
                         },
                         onFailure = {
                             Toast.makeText(context, "스터디 생성 실패", Toast.LENGTH_SHORT).show()
                         }
                     )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SubBlue
                )
            ) {
                Text(text = "생성하기", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}