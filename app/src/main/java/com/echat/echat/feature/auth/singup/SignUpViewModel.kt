package com.echat.echat.feature.auth.singup

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SignUpViewModel: ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Empty)
    val state = _state.asStateFlow()

    private val auth = FirebaseAuth.getInstance()


    fun signUp(name: String, email: String, password: String){
        _state.value = SignUpState.Loading

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {task ->
                        if (task.isSuccessful) {
                            _state.value = SignUpState.Success
                        } else {
                            _state.value = SignUpState.Error(task.exception?.message?: "Signup failed")
                        }
                    }

            }catch (e: Exception){
                _state.value = SignUpState.Error(e.message?:"Unknown error occurred")
            }

        }


    }
}

