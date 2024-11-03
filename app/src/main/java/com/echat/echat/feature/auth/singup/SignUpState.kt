package com.echat.echat.feature.auth.singup

sealed class SignUpState {
    object Success: SignUpState()
    data class Error(val message: String): SignUpState()
    object Loading: SignUpState()
    object Empty: SignUpState()
}