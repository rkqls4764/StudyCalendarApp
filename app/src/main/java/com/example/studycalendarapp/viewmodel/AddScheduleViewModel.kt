package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Schedule
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddScheduleViewModel : ViewModel() {
    private val TAG = "AddScheduleViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _schedule = MutableStateFlow(Schedule())  // 생성할 일정 정보
    val schedule: StateFlow<Schedule> = _schedule

    /* 입력한 값으로 일정 정보 갱신 */
    fun updateSchedule(modifier: (Schedule) -> Schedule) {
        _schedule.value = modifier(_schedule.value)
    }

    /* 일정 생성 함수 */
    fun saveSchedule(studyId: String, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        DB.collection("schedule")
            .add(_schedule.value)
            .addOnSuccessListener { documentRef ->
                val scheduleId = documentRef.id

                // 해당 스터디 문서의 scheduleList에 scheduleId 추가
                DB.collection("study").document(studyId)
                    .update("scheduleList", com.google.firebase.firestore.FieldValue.arrayUnion(scheduleId))
                    .addOnSuccessListener {
                        Log.d(TAG, "일정 생성 성공")
                        Log.d(TAG, "스터디 scheduleList에 일정 추가 성공")
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "스터디 scheduleList에 일정 추가 실패", e)
                        onFailure()
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "일정 생성 실패: ", e)
                onFailure()
            }
    }
}