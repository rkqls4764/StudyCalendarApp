package com.example.studycalendarapp.model

data class User(
    val name: String = "",                      // 이름
    val email: String = "",                     // 이메일
    val studyList: List<String> = emptyList()   // 가입 중인 스터디 아이디 목록
)