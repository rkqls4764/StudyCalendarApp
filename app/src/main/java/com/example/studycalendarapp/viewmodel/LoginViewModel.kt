package com.example.studycalendarapp.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.studycalendarapp.R
import com.example.studycalendarapp.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val TAG = "LoginViewModel"
    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val DB = FirebaseFirestore.getInstance()

    private val _isLoginSuccess = MutableStateFlow(false)   // 로그인 성공 여부 상태
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    fun getGoogleSignInIntent(activity: Activity): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity, gso).signInIntent
    }

    fun handleSignInResult(data: Intent?, onError: (String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account, onError)
        } catch (e: Exception) {
            Log.e(TAG, "Google 로그인 실패: ", e)
            onError("Google 로그인 실패: ${e.message}")
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount, onError: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser

                    // Firestore에 사용자 정보 저장
                    user?.let {
                        val uid = it.uid
                        val userRef = DB.collection("user").document(uid)

                        // Firestore에 사용자 문서 존재 여부 확인
                        userRef.get()
                            .addOnSuccessListener { document ->
                                if (!document.exists()) {
                                    // 문서가 없으면 새로 저장
                                    val userData = User(
                                        name = it.displayName ?: "",
                                        email = it.email ?: "",
                                        studyList = emptyList()
                                    )
                                    userRef.set(userData)
                                        .addOnSuccessListener {
                                            Log.d(TAG, "사용자 정보 저장 성공")
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e(TAG, "사용자 정보 저장 실패: ", e)
                                        }
                                } else {
                                    Log.d(TAG, "등록된 사용자")
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "사용자 정보 조회 실패: ", e)
                            }
                    }

                    Log.d(TAG, "Firebase 로그인 성공: ${firebaseAuth.currentUser?.email}")
                    _isLoginSuccess.value = true
                } else {
                    Log.e(TAG, "Firebase 로그인 실패: ", task.exception)
                    onError("Firebase 로그인 실패: ${task.exception?.message}")
                }
            }
    }
}