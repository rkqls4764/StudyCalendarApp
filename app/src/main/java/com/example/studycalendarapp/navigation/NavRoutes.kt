package com.example.studycalendarapp.navigation

enum class Screen(val route: String) {
    AddSchedule("addSchedule/{studyId}"),
    AddStudy("addStudy"),
    Calendar("calendar/{studyId}"),
    Chating("chating"),
    DetailSchedule("detailSchedule"),
    DetailStudy("detailStudy/{studyId}"),
    JoiningStudy("joiningStudy"),
    JoinStudy("joinStudy/{studyId}"),
    Login("login"),
    Recruit("recruit");
}