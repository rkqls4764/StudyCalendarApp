package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Schedule
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalendarViewModel : ViewModel() {
    private val TAG = "CalendarViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _studyName = MutableStateFlow<String>("") // 스터디 정보
    val studyName: StateFlow<String> = _studyName

    private val _scheduleList = MutableStateFlow<List<Schedule>>(emptyList()) // 일정 목록
    val scheduleList: StateFlow<List<Schedule>> = _scheduleList

    /* 스터디 id로 스터디 이름 조회 함수 */
    fun fetchStudyById(studyId: String) {
        DB.collection("study").document(studyId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name")
                    if (name != null) {
                        _studyName.value = name
                    }
                    Log.d(TAG, "스터디 이름 불러오기 성공: ${_studyName.value}")
                } else {
                    Log.e(TAG, "스터디 문서 없음: ${studyId}")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 정보 불러오기 실패: ", e)
            }
    }

    /* 일정 목록 조회 함수 */
    fun fetchScheduleList(studyId: String) {
        DB.collection("study")
            .document(studyId).collection("schedule")
            .get()
            .addOnSuccessListener { result ->
                val schedules = result.map { it.toObject(Schedule::class.java) }
                _scheduleList.value = schedules
                Log.d(TAG, "일정 목록 가져오기 성공: ${_scheduleList.value}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "일정 목록 가져오기 실패", e)
            }
    }
}