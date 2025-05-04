package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StudyItem(studyName: String, buttonName: String, onButtonClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = studyName,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = onButtonClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(buttonName)
            }
        }
    }
}