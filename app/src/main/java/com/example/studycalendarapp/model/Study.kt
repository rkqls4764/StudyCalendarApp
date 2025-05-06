package com.example.studycalendarapp.model

data class Study(
    val name: String = "",                      // 이름
    val description: String = "",               // 설명
    val method: String = "",                    // 방식
    val date: String = "",                      // 날짜
    val time: String = "",                      // 시간
    val tag: List<String> = emptyList(),        // 태그
    val hostId: String = "",                    // 방장 아이디
    val memberList: List<String> = emptyList()  // 가입한 사용자 아이디 목록
)