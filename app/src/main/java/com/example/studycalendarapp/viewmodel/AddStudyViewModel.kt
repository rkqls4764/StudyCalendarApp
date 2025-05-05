package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Study
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddStudyViewModel : ViewModel() {
    private val TAG = "AddStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _study = MutableStateFlow(Study())  // 생성할 스터디 정보
    val study: StateFlow<Study> = _study

    /* 입력한 값으로 스터디 정보 갱신 */
    fun updateStudy(modifier: (Study) -> Study) {
        _study.value = modifier(_study.value)
    }

    /* 스터디 생성 함수 */
    fun saveStudy(onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        DB.collection("study")
            .add(_study.value)
            .addOnSuccessListener {
                Log.d(TAG, "스터디 생성 성공: ${_study.value}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 생성 실패: ", e)
                onFailure()
            }
    }
}