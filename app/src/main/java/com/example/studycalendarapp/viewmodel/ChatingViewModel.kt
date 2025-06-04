package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studycalendarapp.BuildConfig
import com.example.studycalendarapp.model.ChatRequest
import com.example.studycalendarapp.model.Message
import com.example.studycalendarapp.retrofit.OpenAIApi
import com.example.studycalendarapp.retrofit.RetrofitClient.retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatingViewModel : ViewModel() {
    val openAIApi = retrofit.create(OpenAIApi::class.java)

    private val _chatHistory = MutableStateFlow<List<Message>>(emptyList())  // 채팅 내역
    val chatHistory: StateFlow<List<Message>> = _chatHistory

    fun sendMessage(userInput: String) {
        val userMessage = Message("user", userInput)
        _chatHistory.value += userMessage
        Log.d("ChatLog", "[보냄] user: $userInput")

        viewModelScope.launch {
            try {
                val request = ChatRequest(messages = _chatHistory.value)
                val authHeader = "Bearer ${BuildConfig.OPENAI_API_KEY}"

                val response = openAIApi.getChatResponse(authHeader, request)

                val assistantMessage = response.choices.firstOrNull()?.message
                if (assistantMessage != null) {
                    Log.d("ChatLog", "[응답] assistant: ${assistantMessage.content}")
                    _chatHistory.value += assistantMessage
                } else {
                    Log.d("ChatLog", "[응답 없음] OpenAI 응답에 assistant 메시지가 없습니다.")
                }

            } catch (e: Exception) {
                Log.e("ChatLog", "[에러] OpenAI API 호출 중 오류 발생", e)
            }
        }

//        viewModelScope.launch {
//            try {
//                val request = ChatRequest(messages = _chatHistory.value)
//                val response = openAIApi.getChatResponse(request)
//
//                val assistantMessage = response.choices.firstOrNull()?.message
//                if (assistantMessage != null) {
//                    Log.d("ChatLog", "[응답] assistant: ${assistantMessage.content}")
//                    _chatHistory.value += assistantMessage
//                } else {
//                    Log.d("ChatLog", "[응답 없음] OpenAI 응답에 assistant 메시지가 없습니다.")
//                }
//            } catch (e: Exception) {
//                Log.e("ChatLog", "[에러] OpenAI API 호출 중 오류 발생", e)
//            }
//        }
    }
}