package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studycalendarapp.model.Message

@Composable
fun ChatHistory(modifier: Modifier = Modifier, chatHistory: List<Message>) {
    LazyColumn(
        modifier = modifier.padding(top = 22.dp)
    ) {
        items(chatHistory) { message ->
            val isUser = message.role == "user"

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isUser) ButtonBackDeep else ButtonBack,
                    tonalElevation = 2.dp
                ) {
                    Text(
                        text = message.content,
                        modifier = Modifier
                            .padding(12.dp)
                            .widthIn(max = 240.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}