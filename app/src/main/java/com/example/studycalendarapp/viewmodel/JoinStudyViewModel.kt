package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JoinStudyViewModel : ViewModel() {
    private val TAG = "JoiningStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

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

    /* 스터디 참가 함수 */
    fun joinStudy(studyId: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val studyRef = DB.collection("study").document(studyId)
        val userRef = DB.collection("user").document(uid)

        // Study 문서의 memberList에 uid 추가
        studyRef.update("memberList", FieldValue.arrayUnion(uid))
            .addOnSuccessListener {
                Log.d(TAG, "스터디 memberList 업데이트 성공")

                // User 문서의 studyList에 studyId 추가
                userRef.update("studyList", FieldValue.arrayUnion(studyId))
                    .addOnSuccessListener {
                        Log.d(TAG, "사용자 studyList 업데이트 성공")
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "사용자 studyList 업데이트 실패", e)
                        onFailure()
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 memberList 업데이트 실패", e)
                onFailure()
            }
    }
}