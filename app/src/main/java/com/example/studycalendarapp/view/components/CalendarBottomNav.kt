package com.example.studycalendarapp.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.studycalendarapp.R

sealed class CalendarBottomNavItems(
    val id: String,
    val title: String,
    val icon: Int
) {
    object Calendar: CalendarBottomNavItems(
        id = "calendar",
        title = "캘린더",
        icon = R.drawable.ic_calendar
    )
    object Chating: CalendarBottomNavItems(
        id = "chating",
        title = "채팅",
        icon = R.drawable.ic_chatbot
    )
    object DetailStudy: CalendarBottomNavItems(
        id = "detailStudy",
        title = "스터디 정보",
        icon = R.drawable.ic_detail_study
    )
}

@Composable
fun CalendarBottomNavigationBar(navController: NavController, onItemClick: (id: String) -> Unit) {
    val items = listOf(
        CalendarBottomNavItems.Calendar,
        CalendarBottomNavItems.Chating,
        CalendarBottomNavItems.DetailStudy
    )

    BottomNavigation(
        backgroundColor = MainBlue
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.id,
                onClick = {
                          onItemClick(item.id)
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(42.dp)
                    )
                }
            )
        }
    }
}