package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailStudyViewModel : ViewModel() {
    private val TAG = "DetailStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _study = MutableStateFlow<Study?>(null)
    val study: StateFlow<Study?> = _study

    /* 스터디 id로 스터디 정보 조회 함수 */
    fun fetchStudyById(studyId: String) {
        DB.collection("study").document(studyId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val studyObject = document.toObject(Study::class.java)
                    _study.value = studyObject
                    Log.d(TAG, "스터디 정보 불러오기 성공: ${studyObject}")
                } else {
                    Log.e(TAG, "스터디 문서 없음: ${studyId}")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 정보 불러오기 실패: ", e)
            }
    }
}