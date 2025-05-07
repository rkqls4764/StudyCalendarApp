package com.example.studycalendarapp.model

import java.time.LocalDate

data class Schedule(
    val name: String = "",                          // 이름
    val date: String = LocalDate.now().toString(),  // 날짜
    val time: String = "",                          // 시간
    val description: String = ""                    // 설명
)