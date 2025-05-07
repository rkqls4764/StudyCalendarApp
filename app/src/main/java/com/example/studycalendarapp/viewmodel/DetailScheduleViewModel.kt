package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Schedule
import com.example.studycalendarapp.model.Study
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailScheduleViewModel : ViewModel() {
    private val TAG = "DetailStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _schedule = MutableStateFlow<Schedule?>(null)
    val schedule: StateFlow<Schedule?> = _schedule

    /* 일정 id로 일정 정보 조회 함수 */
    fun fetchScheduleById(scheduleId: String) {
        DB.collection("schedule").document(scheduleId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val scheduleObject = document.toObject(Schedule::class.java)
                    _schedule.value = scheduleObject
                    Log.d(TAG, "일정 정보 불러오기 성공: ${scheduleObject}")
                } else {
                    Log.e(TAG, "일정 문서 없음: ${scheduleId}")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "일정 정보 불러오기 실패: ", e)
            }
    }
}