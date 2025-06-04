package com.example.studycalendarapp.retrofit

import androidx.compose.ui.res.stringResource
import com.example.studycalendarapp.R
import com.example.studycalendarapp.model.ChatRequest
import com.example.studycalendarapp.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {
    @POST("chat/completions")
    suspend fun getChatResponse(
        @Header("Authorization") authHeader: String,
        @Body request: ChatRequest
    ): ChatResponse
}