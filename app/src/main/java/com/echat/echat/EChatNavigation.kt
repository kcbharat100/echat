package com.echat.echat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.echat.echat.feature.auth.home.HomeScreen
import com.echat.echat.feature.auth.signin.SignInScreen
import com.echat.echat.feature.auth.signin.SignUpScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun EChatNav() {
    Surface(modifier = Modifier.fillMaxSize()) {

        val navController = rememberNavController()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val start = if (currentUser != null) "home_screen" else "login_screen"

        NavHost(navController = navController, startDestination = start){

            composable("signin_screen"){
                SignInScreen(navController)

            }

            composable("signup_screen"){
                SignUpScreen(navController)
            }

            composable("home_screen"){
                HomeScreen(navController)
            }

        }

    }
}