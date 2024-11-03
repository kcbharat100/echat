package com.echat.echat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.echat.echat.feature.auth.signin.SignInScreen
import com.echat.echat.feature.auth.signin.SignUpScreen


@Composable
fun EChatNav() {
    Surface(modifier = Modifier.fillMaxSize()) {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "signin_screen"){

            composable("signin_screen"){
                SignInScreen(navController)

            }

            composable("signup_screen"){
                SignUpScreen(navController)
            }

        }

    }
}