package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun StudyItem(study: Study, onButtonClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(vertical = 13.5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = study.name,
                fontSize = 16.sp,
                color = MainText
            )
            Text(
                text = study.date + " " + study.time,
                fontSize = 14.sp,
                color = SubText
            )
        }

        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .width(84.dp)
                .height(32.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonBack
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "일정 관리",
                fontSize = 14.sp,
                color = MainText
            )
        }
    }
}