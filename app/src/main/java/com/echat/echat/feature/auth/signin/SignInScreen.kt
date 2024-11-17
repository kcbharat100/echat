package com.echat.echat.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.echat.echat.R

@Composable
fun SignInScreen(navController: NavController) {
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is SignInState.Success -> {
                //Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                navController.navigate("home_screen"){
                    // Clear the backstack
                    popUpTo("signin_screen") {
                        inclusive = true
                    }
                }

            }
            is SignInState.Error -> {
                Toast.makeText(context, (uiState.value as SignInState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        SignInContent(
            email = email,
            password = password,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onSignInClick = { viewModel.signIn(email, password) },
            onSignUpClick = { navController.navigate("signup") },
            isLoading = uiState.value == SignInState.Loading,
            isSignInEnabled = email.isNotEmpty() && password.isNotEmpty() && uiState.value != SignInState.Loading,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun SignInContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    isLoading: Boolean,
    isSignInEnabled: Boolean,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(16.dp))
        EmailInput(email = email, onEmailChange = onEmailChange)
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(password = password, onPasswordChange = onPasswordChange)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            SignInButton(onClick = onSignInClick, isEnabled = isSignInEnabled)
            SignUpLink(onClick = onSignUpClick)
        }
    }
}

@Composable
fun AppLogo() {
    Image(
        painter = painterResource(id = R.drawable.echat_logo_test),
        contentDescription = null,
        modifier = Modifier
            .size(200.dp)
            .background(Color.White)
    )
}

@Composable
fun EmailInput(email: String, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") }
    )
}

@Composable
fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Password") },
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun SignInButton(onClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = isEnabled
    ) {
        Text(text = "Sign In")
    }
}

@Composable
fun SignUpLink(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = "Don't have an account? Sign Up")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    SignInScreen(navController = rememberNavController())
}
