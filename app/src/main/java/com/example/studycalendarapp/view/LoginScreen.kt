package com.example.studycalendarapp.view

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.studycalendarapp.R
import com.example.studycalendarapp.view.components.MainBlue
import com.example.studycalendarapp.viewmodel.LoginViewModel

/* 로그인 화면 */
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    val viewModel: LoginViewModel = viewModel()

    val isLoginSuccess by viewModel.isLoginSuccess.collectAsState() // 로그인 성공 여부

    // 로그인 성공 시 화면 이동
    LaunchedEffect(isLoginSuccess) {
        if (isLoginSuccess) {
            navController.navigate("joiningStudy")
        }
    }

    // launcher: Intent 실행 → 결과만 ViewModel로 넘김
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleSignInResult(
            data = result.data,
            onError = { Log.e("LoginScreen", it) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "스터디 모집 및 관리 앱",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(320.dp))

        Divider(modifier = Modifier.width(340.dp), color = Color.White, thickness = 1.dp)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "로그인하고 시작하기",
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(46.dp)
                .clickable {
                    val signInIntent = viewModel.getGoogleSignInIntent(activity)
                    launcher.launch(signInIntent)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_login_btn),
                contentDescription = "구글 로그인 버튼",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}