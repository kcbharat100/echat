package com.echat.echat.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.echat.echat.R

@Composable
fun SignInScreen( navController: NavController) {
    // UI for the sign-in screen

    //var viewModel = viewModel<SignInViewModel>()
    var viewModel = hiltViewModel<SignInViewModel>()

    val uiState = viewModel.state.collectAsState()


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}

    Scaffold(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(painter = painterResource(id = R.drawable.echat_logo_test),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                )
        Row {
            Text(text = stringResource(id = R.string.app_name),
                fontStyle = FontStyle.Italic
            )

            Text(text = stringResource(id = R.string.app_slogan),
                fontStyle = FontStyle.Italic)
        }


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = stringResource(R.string.password)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.signIn(email, password) },
                enabled = email.isNotEmpty() && password.isNotEmpty()

            ) {
                Text(text = "Sign In")
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("signup_screen") }
                ) {
                Text(text = "Don't have an account? Create an account")
            }
            Spacer(modifier = Modifier.height(16.dp))

            when(uiState.value){
                is SignInState.Loading -> {
                    // Show loading indicator
                    CircularProgressIndicator()
                }
                is SignInState.Success -> {
                    // Navigate to the next screen
                    //navController.navigate("home_screen")
                    Toast.makeText(LocalContext.current, "User signed in successfully!", Toast.LENGTH_SHORT).show()
                }
                is SignInState.Error -> {
                    // Show error message
                    Toast.makeText(LocalContext.current, (uiState.value as SignInState.Error).message, Toast.LENGTH_SHORT).show()
                }
                else -> { }
            }


        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}