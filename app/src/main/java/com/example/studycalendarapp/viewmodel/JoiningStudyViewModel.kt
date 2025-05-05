package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studycalendarapp.model.Study
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JoiningStudyViewModel : ViewModel() {
    private val TAG = "JoiningStudyViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _studyList = MutableStateFlow<List<Study>>(emptyList()) // 스터디 목록
    val studyList: StateFlow<List<Study>> = _studyList

    fun fetchStudies() {
        viewModelScope.launch {
            DB.collection("study")
                .get()
                .addOnSuccessListener { result ->
                    val studies = result.map {
                        it.toObject(Study::class.java)
                    }
                    _studyList.value = studies
                    Log.d(TAG, "스터디 목록 불러오기 성공: ${_studyList.value}")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "스터디 목록 불러오기 실패: ", e)
                }
        }
    }
}