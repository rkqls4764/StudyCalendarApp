package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddStudyViewModel : ViewModel() {
    private val TAG = "AddStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    private val _study = MutableStateFlow(Study())  // 생성할 스터디 정보
    val study: StateFlow<Study> = _study

    /* 입력한 값으로 스터디 정보 갱신 */
    fun updateStudy(modifier: (Study) -> Study) {
        _study.value = modifier(_study.value)
    }

    /* 스터디 생성 함수 */
    fun saveStudy(onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        val studyWithUid = _study.value.copy(
            hostId = uid,               // 방장 아이디 = 현재 사용자 아이디
            memberList = listOf(uid)    // 가입한 사용자 아이디 리스트에 현재 사용자 아이디 추가
        )

        DB.collection("study")
            .add(studyWithUid)
            .addOnSuccessListener { documentRef ->
                addStudyId(documentRef)
                Log.d(TAG, "스터디 생성 성공: ${_study.value}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 생성 실패: ", e)
                onFailure()
            }
    }

    /* 사용자 문서 studyList에 생성한 스터디 아이디 추가 함수 */
    fun addStudyId(documentRef: DocumentReference) {
        DB.collection("user")
            .document(uid)
            .update("studyList", FieldValue.arrayUnion(documentRef.id))
            .addOnSuccessListener {
                Log.d(TAG, "studyList에 생성한 스터디 ID 추가 성공")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "studyList 업데이트 실패: ", e)
            }
    }
}