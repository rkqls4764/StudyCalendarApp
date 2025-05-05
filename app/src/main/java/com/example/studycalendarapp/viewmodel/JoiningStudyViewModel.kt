package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JoiningStudyViewModel : ViewModel() {
    private val TAG = "JoiningStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    private val _studyList = MutableStateFlow<List<Study>>(emptyList()) // 스터디 목록
    val studyList: StateFlow<List<Study>> = _studyList

    /* 가입 중인 스터디 목록 조회 함수 */
    fun fetchStudies() {
        DB.collection("user").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val studyList = document.get("studyList") as? List<String> ?: emptyList()

                    if (studyList.isEmpty()) {
                        _studyList.value = emptyList()
                        Log.d(TAG, "가입한 스터디 없음")
                        return@addOnSuccessListener
                    }

                    // Firestore whereIn은 10개 제한 있음
                    DB.collection("study")
                        .whereIn(FieldPath.documentId(), studyList.take(10))
                        .get()
                        .addOnSuccessListener { result ->
                            val studies = result.map {
                                it.toObject(Study::class.java)
                            }
                            _studyList.value = studies
                            Log.d(TAG, "가입한 스터디 목록 불러오기 성공: ${_studyList.value}")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "스터디 목록 조회 실패: ", e)
                        }
                } else {
                    Log.e(TAG, "사용자 문서 없음")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "사용자 정보 조회 실패: ", e)
            }
    }
}