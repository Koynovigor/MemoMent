package com.leonikl.memoment.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonState
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonType
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSJetPackComposeProgressButton
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            Text(
                text = "by L3on1kL\n" +
                        "beta v 1.0.0",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .alpha(0.4f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val submitButtonState by remember { mutableStateOf(SSButtonState.LOADING) }
            SSJetPackComposeProgressButton(
                type = SSButtonType.WHEEL,
                width = 45.dp,
                height = 45.dp,
                onClick = { },
                assetColor = Color.Gray,
                buttonState = submitButtonState,
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                elevation = null
            )
            LaunchedEffect(
                key1 = true
            ){
                delay(2500)
                navController.navigate("MainScreen"){
                    popUpTo(0)
                }
            }
        }
    }
}