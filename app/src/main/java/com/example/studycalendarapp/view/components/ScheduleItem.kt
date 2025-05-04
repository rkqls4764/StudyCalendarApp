package com.example.studycalendarapp.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScheduleItem(name: String, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detailSchedule") },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}