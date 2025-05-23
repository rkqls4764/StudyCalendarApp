package com.example.studycalendarapp.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BackHandler {
        if (currentRoute == "login" || currentRoute == "joiningStudy") {
            // 앱 종료
            (navController.context as? ComponentActivity)?.finish()
        } else {
            navController.popBackStack()
        }
    }

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.JoiningStudy.route) { JoiningStudyScreen(navController) }
        composable("calendar/{studyId}") { backStackEntry ->
            val studyId = backStackEntry.arguments?.getString("studyId") ?: return@composable
            CalendarScreen(navController, studyId)
        }
        composable("addSchedule/{studyId}") { backStackEntry ->
            val studyId = backStackEntry.arguments?.getString("studyId") ?: return@composable
            AddScheduleScreen(navController, studyId)
        }
        composable(Screen.AddStudy.route) { AddStudyScreen(navController) }
        composable(Screen.Chating.route) { ChatingScreen(navController) }
        composable("detailSchedule/{scheduleId}") { backStackEntry ->
            val scheduleId = backStackEntry.arguments?.getString("scheduleId") ?: return@composable
            DetailScheduleScreen(navController, scheduleId)
        }
        composable("detailStudy/{studyId}") { backStackEntry ->
            val studyId = backStackEntry.arguments?.getString("studyId") ?: return@composable
            DetailStudyScreen(navController, studyId)
        }
        composable("joinStudy/{studyId}") { backStackEntry ->
            val studyId = backStackEntry.arguments?.getString("studyId") ?: return@composable
            JoinStudyScreen(navController, studyId)
        }
        composable(Screen.Recruit.route) { RecruitScreen(navController) }
    }
}