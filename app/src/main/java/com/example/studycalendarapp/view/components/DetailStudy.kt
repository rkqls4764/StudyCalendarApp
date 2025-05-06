package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studycalendarapp.model.Study

@Composable
fun DetailStudy(study: Study) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = study.name, fontWeight = FontWeight.Bold, fontSize = 20.sp) // 이름 출력
    }

    Spacer(modifier = Modifier.padding(bottom = 30.dp))

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = study.description, fontSize = 14.sp)    // 설명 출력
        Spacer(modifier = Modifier.padding(bottom = 55.dp))

        Divider(color = ButtonBackDeep, thickness = 1.dp)
        Spacer(modifier = Modifier.padding(bottom = 30.dp))

        InfoRow(label = "회의", value = "${study.date} ${study.time}") // 회의 날짜, 시간 출력
        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        InfoRow(label = "방식", value = study.method) // 방식 출력
        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        InfoRow(label = "태그", value = study.tag.joinToString(", ")) // 태그 출력
    }
}