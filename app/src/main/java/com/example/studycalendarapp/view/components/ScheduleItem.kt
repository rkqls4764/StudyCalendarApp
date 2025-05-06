package com.example.studycalendarapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavController
import com.example.studycalendarapp.model.Schedule

@Composable
fun ScheduleItem(schedule: Schedule, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { navController.navigate("detailSchedule") }
    ) {
        Text(
            text = schedule.date + " ",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MainText,
            modifier = Modifier.width(70.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = schedule.name,
                fontSize = 14.sp,
                color = MainText
            )

            Text(
                text = schedule.time,
                fontSize = 14.sp,
                color = SubText
            )
        }
    }
}