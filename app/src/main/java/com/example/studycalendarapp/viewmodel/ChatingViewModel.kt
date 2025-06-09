package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studycalendarapp.BuildConfig
import com.example.studycalendarapp.model.ChatRequest
import com.example.studycalendarapp.model.Message
import com.example.studycalendarapp.model.Schedule
import com.example.studycalendarapp.model.Study
import com.example.studycalendarapp.model.User
import com.example.studycalendarapp.retrofit.OpenAIApi
import com.example.studycalendarapp.retrofit.RetrofitClient.retrofit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
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

    init {
        showStartChat() // 첫 진입 시 StartChat 추가
    }

    /* 시작 채팅 추가 함수 */
    fun showStartChat() {
        _chatHistory.value += Message(role = "startchat", content = "")
        _isInputEnabled.value = false
    }

    /* AI 메세지 추가 함수 */
    fun addAIMessage(message: String) {
        _chatHistory.value += Message(role = "assistant", content = message)
        _isInputEnabled.value = true
    }

    /* 관심사 기반 스터디 추천 함수 */
     fun recommendStudy(userInput: String) {
        val userMessage = Message("user", userInput)
        _chatHistory.value += userMessage

        // 사용자가 가입하지 않은 스터디의 id, 이름, 태그 목록
        var targetStudyText = ""
        DB.collection("study")
            .get()
            .addOnSuccessListener { studyResult ->
                val filteredStudyList = studyResult.mapNotNull  { document ->
                    val study = document.toObject(Study::class.java).copy(id = document.id)
                    // 현재 사용자가 가입하지 않은 스터디만 필터링
                    if (uid !in study.memberList && study.hostId != uid) study else null
                }

                targetStudyText = filteredStudyList.joinToString("\n") { study ->
                    "- ${study.id}, ${study.name}, ${study.tag.joinToString("|")}"
                }

                Log.d(TAG, targetStudyText)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 목록 조회 실패: ", e)
            }

        val systemPrompt = """
                    너는 사용자의 관심사에 맞는 스터디를 추천하는 챗봇이다.
                    스터디 목록은 id, name, tag를 가진다.
                    가벼운 코멘트도 포함한다. (스터디에는 제외)
                    스터디는 '{번호}. {이름} ({태그})' 형식 출력한다.
                    추천 가능한 스터디 목록:
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
                showStartChat()
            } catch (e: Exception) {
                Log.e(TAG, "[에러] OpenAI API 호출 중 오류 발생", e)
            }
        }
     }

    /* 사용자가 진행한 일정 기반 맞춤 학습 내용 추천 함수 */
    fun recommendLearningContent(userInput: String) {
        val userMessage = Message("user", userInput)
        _chatHistory.value += userMessage

        // 사용자가 진행한 일정 id, 이름, 설명 목록
        var targetScheduleText = ""
        DB.collection("user").document(uid)
            .get()
            .addOnSuccessListener { userDocument ->
                val studyList = userDocument.get("studyList") as? List<String> ?: emptyList()

                if (studyList.isEmpty()) {
                    targetScheduleText = "참여 중인 스터디가 없습니다."
                    return@addOnSuccessListener
                }

                DB.collection("study")
                    .whereIn(FieldPath.documentId(), studyList)
                    .get()
                    .addOnSuccessListener { studyQuery ->
                        val allScheduleIds = mutableListOf<String>()

                        for (studyDocument in studyQuery) {
                            val scheduleIds =
                                studyDocument.get("scheduleList") as? List<String> ?: emptyList()
                            allScheduleIds.addAll(scheduleIds)
                        }

                        if (allScheduleIds.isEmpty()) {
                            targetScheduleText = "일정이 없습니다."
                            return@addOnSuccessListener
                        }

                        DB.collection("schedule")
                            .whereIn(FieldPath.documentId(), allScheduleIds)
                            .get()
                            .addOnSuccessListener { scheduleQuery ->
                                targetScheduleText =
                                    scheduleQuery.joinToString("\n") { scheduleDocument ->
                                        val schedule =
                                            scheduleDocument.toObject(Schedule::class.java)
                                        "- ${scheduleDocument.id}, ${scheduleDocument.getString("name")}, ${
                                            scheduleDocument.getString(
                                                "description"
                                            )
                                        }"
                                    }
                                Log.d(TAG, targetScheduleText)
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "일정 조회 실패", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "스터디 조회 실패", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "사용자 조회 실패", e)
            }

        val systemPrompt = """
                    너는 사용자가 진행한 일정을 기반으로 다음 학습 내용을 추천하는 챗봇이다.
                    일정 목록은 id, name, description을 가진다.
                    가벼운 코멘트도 포함한다.
                    마지막은 추천한 학습 내용을 '[추천 일정]\n'에 일정 형식(-{name}: {description})으로 끝난다.
                    진행한 일정 목록:
                """.trimIndent()

        val messages = listOf(
            Message("system", "$systemPrompt\n$targetScheduleText"),
            Message("user", userInput)
        )

        viewModelScope.launch {
            try {
                val request = ChatRequest(messages = messages, model = "gpt-4o-mini")
                val authHeader = "Bearer ${BuildConfig.OPENAI_API_KEY}"
                val response = openAIApi.getChatResponse(authHeader, request)
                val result = response.choices.firstOrNull()?.message ?: Message("assistant", "추천 결과가 없습니다.")
                _chatHistory.value += result
                showStartChat()
            } catch (e: Exception) {
                Log.e(TAG, "[에러] OpenAI API 호출 중 오류 발생", e)
            }
        }

    }
}