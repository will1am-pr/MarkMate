package com.example.markmate.ui.screens.user

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.markmate.ui.theme.Blue
import com.example.markmate.ui.theme.Goldenbrown
import java.time.DayOfWeek
import java.time.LocalDate

data class LeaveRequest(val date: LocalDate, val reason: String, val status: String = "Pending")

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun RequestLeaveScreen(navController: NavController) {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek
    val context = LocalContext.current

    var leaveReason by remember { mutableStateOf("") }
    var leaveSubmitted by remember { mutableStateOf(false) }
    val leaveHistory = remember { mutableStateListOf<LeaveRequest>() }
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request Leave") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        bottomBar = {
            NavigationBar(containerColor = Blue) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        // navController.navigate("home_route")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Leave") },
                    label = { Text("Leave") },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        // navController.navigate("leave_route")
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 2,
                    onClick = {
                        selectedIndex = 2
                        // navController.navigate("profile_route")
                    }
                )
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEFF3F8))
                    .padding(paddingValues)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Here, you can request for leave on official non-school days. Please provide a valid reason.",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Date: $today (${dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }})",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                    Text(
                        text = when (dayOfWeek) {
                            DayOfWeek.SATURDAY -> "⚠️ Optional make-up classes. Leave allowed but may affect catch-up progress."
                            else -> "✅ No school today. You can request leave for personal reasons."
                        },
                        color = if (dayOfWeek == DayOfWeek.SATURDAY) Color(0xFFCC6600) else Color(0xFF228B22),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = leaveReason,
                        onValueChange = { leaveReason = it },
                        label = { Text("Reason for leave") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val newRequest = LeaveRequest(today, leaveReason)
                            leaveHistory.add(0, newRequest)
                            leaveSubmitted = true
                            Toast.makeText(context, "Leave request submitted!", Toast.LENGTH_SHORT).show()
                            leaveReason = ""
                        },
                        enabled = leaveReason.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit Leave Request", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (leaveSubmitted) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF6DD))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("✅ Leave request summary:", fontWeight = FontWeight.Bold)
                                Text("• Date: $today")
                                Text("• Reason: Submitted")
                                Text("• Status: Pending review")
                            }
                        }
                    }
                } else {
                    Text(
                        text = "❌ Leave requests can only be made during weekends (Saturday or Sunday).",
                        color = Color.Red,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "🗓️ Leave Request History",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF003366)
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (leaveHistory.isEmpty()) {
                    Text(
                        text = "No past leave requests found.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxHeight(0.4f)) {
                        items(leaveHistory) { request ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("📅 ${request.date} - ${request.reason}", fontWeight = FontWeight.Medium)
                                    Text("Status: ${request.status}", color = Color(0xFF555555))
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RequestLeaveScreenPreview() {
    RequestLeaveScreen(navController = rememberNavController())
}
