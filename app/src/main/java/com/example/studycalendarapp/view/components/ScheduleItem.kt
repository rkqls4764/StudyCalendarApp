package com.example.studycalendarapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studycalendarapp.model.Schedule
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ScheduleItem(schedule: Schedule, onButtonClick: () -> Unit) {
    val dateFormatted = try {
        val parsedDate = LocalDate.parse(schedule.date) // "yyyy-MM-dd"
        val day = parsedDate.dayOfMonth.toString()
        val dayOfWeek = parsedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) // "월", "화", ...
        "$day ($dayOfWeek)"
    } catch (e: Exception) {
        schedule.date
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onButtonClick() }
    ) {
        Text(
            text = dateFormatted,
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