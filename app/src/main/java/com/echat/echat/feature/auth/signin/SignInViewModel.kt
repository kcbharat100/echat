package com.echat.echat.feature.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val _state = MutableStateFlow<SignInState>(SignInState.Initial)
    val state = _state.asStateFlow()

    fun signIn(email: String, password: String){
        _state.value = SignInState.Loading

        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            _state.value = SignInState.Success
                        } else {
                            _state.value = SignInState.Error(it.exception?.message ?: "Signin failed")
                        }
                    }

            } catch (e: Exception){
                _state.value = SignInState.Error(e.message ?: "Unknown error occurred")
            }

        }


    }



}