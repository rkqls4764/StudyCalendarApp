package com.example.studycalendarapp.view.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/* 날짜 선택 요소 */
@Composable
fun DatePickerField(label: String, date: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val parsedDate = try {
        LocalDate.parse(date, dateFormatter)
    } catch (e: Exception) {
        LocalDate.now()
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selected = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selected.format(dateFormatter))
        },
        parsedDate.year,
        parsedDate.monthValue - 1,
        parsedDate.dayOfMonth
    )

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.height(56.dp).weight(2f).clickable { datePickerDialog.show() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = date)
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}