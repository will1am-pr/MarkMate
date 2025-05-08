package com.example.markmate.ui.screens.splash

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.markmate.R
import com.example.markmate.navigation.ROUT_LOGIN
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {
    val coroutine = rememberCoroutineScope()
    coroutine.launch {
        delay(2000)
        navController.navigate(ROUT_LOGIN)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top text
            Text(
                text = "Mark the moment.\nTrack with ease.",
                fontSize = 22.sp,
                color = Color(0xFF003366),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            // Image with fixed height to preserve visibility
            Image(
                painter = painterResource(id = R.drawable.atttendance),
                contentDescription = "Attendance Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp) // You can adjust this based on your image size
                    .padding(vertical = 16.dp)
            )

            // Bottom text and button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome to MarkMate",
                    fontSize = 20.sp,
                    color = Color(0xFF0033CC),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                val context = LocalContext.current
                Button(onClick = {
                    Toast.makeText(context, "Let's get started!", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Get Started")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
