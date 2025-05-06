package com.example.studycalendarapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.model.Study
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class RecruitViewModel : ViewModel() {
    private val TAG = "RecruitViewModel"
    private val DB = FirebaseFirestore.getInstance()

    private val _studyList = MutableStateFlow<List<Study>>(emptyList()) // 스터디 목록
    private val _filteredStudyList = MutableStateFlow<List<Study>>(emptyList()) // 필터링된 스터디 목록
    val filteredStudyList: StateFlow<List<Study>> = _filteredStudyList

    private val _searchText = MutableStateFlow<String>("") // 검색어
    val searchText: StateFlow<String> = _searchText

    /* 입력한 값으로 검색어 갱신 함수 */
    fun updateSearchText(newText: String) {
        _searchText.value = newText
        _filteredStudyList.value = filterStudyList(newText)
    }

    /* 검색어 기준으로 필터링된 스터디 목록 조회 함수 */
    private fun filterStudyList(query: String): List<Study> {
        return if (query.isBlank()) {
            _studyList.value
        } else {
            _studyList.value.filter { study ->
                study.name.contains(query, ignoreCase = true) ||
                study.tag.any { it.contains(query, ignoreCase = true) }
            }
        }
    }

    /* 스터디 목록 조회 함수 */
    fun fetchStudyList() {
        DB.collection("study")
            .get()
            .addOnSuccessListener { result ->
                val studies = result.map {
                    it.toObject(Study::class.java)
                }
                _studyList.value = studies
                _filteredStudyList.value = filterStudyList(_searchText.value)
                Log.d(TAG, "스터디 목록 불러오기 성공: ${_studyList.value}")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "스터디 목록 조회 실패: ", e)
            }
    }
}