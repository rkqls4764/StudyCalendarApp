package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studycalendarapp.BuildConfig
import com.example.studycalendarapp.model.ChatRequest
import com.example.studycalendarapp.model.Message
import com.example.studycalendarapp.model.Study
import com.example.studycalendarapp.model.User
import com.example.studycalendarapp.retrofit.OpenAIApi
import com.example.studycalendarapp.retrofit.RetrofitClient.retrofit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatingViewModel : ViewModel() {
    private val TAG = "ChatingViewModel"
    private val DB = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid
    val openAIApi = retrofit.create(OpenAIApi::class.java)

    private val _chatHistory = MutableStateFlow<List<Message>>(emptyList())  // 채팅 내역
    val chatHistory: StateFlow<List<Message>> = _chatHistory
    private val _isInputEnabled = MutableStateFlow(false)   // 입력 가능 여부
    val isInputEnabled: StateFlow<Boolean> = _isInputEnabled

    /* AI 메세지 추가 함수 */
    fun addAIMessage(message: String) {
        _chatHistory.value += Message(role = "assistant", content = message)
        _isInputEnabled.value = true
    }

    /* 관심사 기반 스터디 추천 함수 */
     fun recommendStudy(userInput: String) {
        val userMessage = Message("user", userInput)
        _chatHistory.value += userMessage

        DB.collection("study")
            .get()
            .addOnSuccessListener { studyResult ->
                val filteredStudyList = studyResult.mapNotNull  { document ->
                    val study = document.toObject(Study::class.java).copy(id = document.id)
                    // 현재 사용자가 가입하지 않은 스터디만 필터링
                    if (uid !in study.memberList && study.hostId != uid) study else null
                }

                val targetStudyText = filteredStudyList.joinToString("\n") { study ->
                    "- ${study.id}, ${study.name}, ${study.tag.joinToString("|")}"
                }

                Log.d(TAG, targetStudyText)

                val systemPrompt = """
                    너는 사용자의 관심사에 맞는 스터디를 추천하는 챗봇이다.
                    스터디는 이름(name)과 태그(tag)를 가진다.
                    사용자가 아직 가입하지 않은 스터디 중에서, 관심사와 관련 있는 스터디를 골라라. 마크다운 제거.
                    아래는 추천 가능한 스터디 목록이다:
                """.trimIndent()

                val messages = listOf(
                    Message("system", "$systemPrompt\n$targetStudyText"),
                    Message("user", userInput)
                )

                viewModelScope.launch {
                    try {
                        val request = ChatRequest(messages = messages, model = "gpt-4o-mini")
                        val authHeader = "Bearer ${BuildConfig.OPENAI_API_KEY}"
                        val response = openAIApi.getChatResponse(authHeader, request)
                        val result = response.choices.firstOrNull()?.message ?: Message("assistant", "추천 결과가 없습니다.")
                        _chatHistory.value += result
                    } catch (e: Exception) {
                        Log.e(TAG, "[에러] OpenAI API 호출 중 오류 발생", e)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 목록 조회 실패: ", e)
            }
     }

    /* 맞춤 학습 내용 추천 함수 */
    fun recommendLearningContent() {

    }
}