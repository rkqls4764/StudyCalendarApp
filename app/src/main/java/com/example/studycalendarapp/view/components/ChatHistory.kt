package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studycalendarapp.R
import com.example.studycalendarapp.model.Message

@Composable
fun ChatHistory(
    modifier: Modifier = Modifier,
    chatHistory: List<Message>,
    onRecommendStudyClick: () -> Unit,
    onRecommendLearningClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(vertical = 22.dp)
    ) {
        items(chatHistory) { message ->
            when (message.role) {
                "startchat" -> {
                    StartChat(
                        onRecommendStudyClick = onRecommendStudyClick,
                        onRecommendLearningClick = onRecommendLearningClick
                    )
                }

                else -> {
                    val isUser = message.role == "user"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isUser) {
                            Icon(
                                painter = painterResource(id = R.drawable.img_ai_profile),
                                contentDescription = "AI 프로필",
                                modifier = Modifier.padding(end = 8.dp).size(26.dp)
                            )
                        }

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
    }
}