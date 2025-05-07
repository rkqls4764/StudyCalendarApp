package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studycalendarapp.model.Schedule
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class CalendarViewModel : ViewModel() {
    private val TAG = "CalendarViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _studyName = MutableStateFlow("") // 스터디 이름
    val studyName: StateFlow<String> = _studyName

    private val _selectedDate = MutableStateFlow(LocalDate.now()) // 선택한 날짜
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _allScheduleList = MutableStateFlow<List<Schedule>>(emptyList()) // 모든 일정 목록

    // 선택한 날짜 기준으로 필터링된 일정 목록 (날짜 기준 오름차순 정렬)
    val scheduleList: StateFlow<List<Schedule>> = combine(_selectedDate, _allScheduleList) { selectedDate, allSchedules ->
        allSchedules
            .filter { schedule ->
                try {
                    val scheduleDate = LocalDate.parse(schedule.date)
                    scheduleDate.year == selectedDate.year && scheduleDate.month == selectedDate.month
                } catch (e: Exception) {
                    Log.e(TAG, "날짜 파싱 오류: ${schedule.date}", e)
                    false
                }
            }
            .sortedBy { LocalDate.parse(it.date) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /* 선택한 날짜 업데이트 함수 */
    fun updateSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        Log.d(TAG, date.toString())
    }

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
                _allScheduleList.value = schedules
                Log.d(TAG, "일정 목록 가져오기 성공: ${_allScheduleList.value}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "일정 목록 가져오기 실패", e)
            }
    }
}