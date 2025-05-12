package com.example.markmate.ui.screens.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.ui.tooling.preview.Preview
import com.example.markmate.navigation.ROUT_HOME
import com.example.markmate.navigation.ROUT_VIEWMYATTENDANCE
import java.time.LocalDate

@SuppressLint("NewApi")
@Composable
fun ViewMyAttendanceScreen(navController: NavController) {
    val context = LocalContext.current
    val currentUser = "john.doe@student.com"
    val today = LocalDate.now().toString()

    var attendanceMarked by remember { mutableStateOf(false) }
    val isStudentPresentToday = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            StudentBottomBar(currentRoute = ROUT_VIEWMYATTENDANCE) {
                navController.navigate(ROUT_HOME)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFEBECEF))
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Attendance",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Date: $today",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (!isStudentPresentToday.value) {
                Text(
                    text = "You were absent today. Attendance marking disabled.",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            } else if (attendanceMarked) {
                Text(
                    text = "You already marked attendance for today.",
                    color = Color(0xFF228B22),
                    fontSize = 16.sp
                )
            } else {
                Button(
                    onClick = {
                        attendanceMarked = true
                        Toast.makeText(context, "Attendance marked!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0033CC))
                ) {
                    Text("Mark Attendance", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Attendance Policy",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF003366)
            )

            Text(
                text = "Only present students can mark attendance.\nAttendance is locked to your account only.",
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewMyAttendanceScreenPreview() {
    ViewMyAttendanceScreen(navController = rememberNavController())
}
