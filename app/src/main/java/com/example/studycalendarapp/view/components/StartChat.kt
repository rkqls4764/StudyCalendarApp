package com.example.studycalendarapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studycalendarapp.R
import com.example.studycalendarapp.viewmodel.ChatingViewModel

@Composable
fun StartChat(onRecommendStudyClick: () -> Unit, onRecommendLearningClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.img_ai_profile),
            contentDescription = "AI 프로필",
            modifier = Modifier.padding(end = 8.dp).size(26.dp)
        )

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = ButtonBack,
            tonalElevation = 2.dp
        ) {
            Column {
                Text(
                    text = "무엇을 도와드릴까요?",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .widthIn(max = 240.dp),
                    color = Color.Black
                )

                Button(
                    modifier = Modifier
                        .width(210.dp)
                        .height(46.dp)
                        .padding(horizontal = 14.dp)
                        .padding(bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SubBlue),
                    onClick = { onRecommendStudyClick() }
                ) {
                    Text(text = "스터디 추천 받기", fontSize = 14.sp, color = Color.Black, textAlign = TextAlign.Center)
                }

                Button(
                    modifier = Modifier
                        .width(210.dp)
                        .height(46.dp)
                        .padding(horizontal = 14.dp)
                        .padding(bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SubBlue),
                    onClick = { onRecommendLearningClick() }
                ) {
                    Text(text = "학습 내용 추천 받기", fontSize = 14.sp, color = Color.Black, textAlign = TextAlign.Center)
                }
            }
        }
    }
}