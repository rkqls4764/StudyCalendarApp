package com.example.studycalendarapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studycalendarapp.view.AddScheduleScreen
import com.example.studycalendarapp.view.AddStudyScreen
import com.example.studycalendarapp.view.CalendarScreen
import com.example.studycalendarapp.view.ChatingScreen
import com.example.studycalendarapp.view.DetailScheduleScreen
import com.example.studycalendarapp.view.DetailStudyScreen
import com.example.studycalendarapp.view.JoinStudyScreen
import com.example.studycalendarapp.view.JoiningStudyScreen
import com.example.studycalendarapp.view.LoginScreen
import com.example.studycalendarapp.view.RecruitScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.JoiningStudy.route) { JoiningStudyScreen(navController) }
        composable(Screen.Calendar.route) { CalendarScreen(navController) }
        composable(Screen.AddSchedule.route) { AddScheduleScreen(navController) }
        composable(Screen.AddStudy.route) { AddStudyScreen(navController) }
        composable(Screen.Chating.route) { ChatingScreen(navController) }
        composable(Screen.DetailSchedule.route) { DetailScheduleScreen(navController) }
        composable(Screen.DetailStudy.route) { DetailStudyScreen(navController) }
        composable("joinStudy/{studyId}") { backStackEntry ->
            val studyId = backStackEntry.arguments?.getString("studyId") ?: return@composable
            JoinStudyScreen(navController, studyId)
        }
        composable(Screen.Recruit.route) { RecruitScreen(navController) }
    }
}