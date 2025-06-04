package com.example.studycalendarapp.model

data class ChatRequest(
    val model: String = "gpt-4o-mini",
    val messages: List<Message>
)
data class ChatResponse(
    val choices: List<Choice>
)
data class Choice(
    val message: Message
)
data class Message(
    val role: String,
    val content: String
)