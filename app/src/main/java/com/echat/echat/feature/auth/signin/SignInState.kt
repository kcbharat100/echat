package com.echat.echat.feature.auth.signin

sealed class SignInState{
    object Initial: SignInState()
    object Loading: SignInState()
    object Success: SignInState()
    data class Error(val message: String): SignInState()

}