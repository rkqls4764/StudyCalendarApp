package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studycalendarapp.model.Schedule

@Composable
fun DetailSchedule(schedule: Schedule) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(bottom = 30.dp))

        ScheduleRow(label = "이름", value = schedule.name)
        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        ScheduleRow(label = "날짜", value = schedule.date)
        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        ScheduleRow(label = "시간", value = schedule.time)
        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        ScheduleRow(label = "설명", value = schedule.description)
        Spacer(modifier = Modifier.padding(bottom = 25.dp))
    }
}

@Composable
fun ScheduleRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.width(50.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = label,
//                fontWeight = FontWeight.Bold,
//                fontSize = 14.sp
//            )
//        }
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.weight(3f)
//        ) {
//            Text(
//                text = value,
//                fontSize = 14.sp
//            )
//        }
    }
}