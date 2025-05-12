package com.example.markmate.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

// Mock data structure
val studentAttendanceData = mapOf(
    "Full-stack MIT Software Development" to listOf("Alice - Present", "Bob - Absent", "Charlie - Present"),
    "Cybersecurity" to listOf("Diana - Present", "Ethan - Present", "Fay - Absent"),
    "Data Science" to listOf("George - Absent", "Hannah - Present", "Ian - Present")
)

@Composable
fun ViewAttendanceScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF003366)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Add Student") },
                    label = { Text("Add") },
                    selected = false,
                    onClick = { navController.navigate("add_student") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Manage Classes") },
                    label = { Text("Classes") },
                    selected = false,
                    onClick = { navController.navigate("manage_classes") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Attendance") },
                    label = { Text("Attendance") },
                    selected = true,
                    onClick = { /* Already here */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Leave Requests") },
                    label = { Text("Leave") },
                    selected = false,
                    onClick = { navController.navigate("view_leave_requests") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF6F9FC))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "View Attendance",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                studentAttendanceData.forEach { (course, students) ->
                    item {
                        Text(
                            text = course,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color(0xFF0033CC)
                        )
                    }
                    items(students) { student ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(student, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewAttendanceScreenPreview() {
    ViewAttendanceScreen(navController = rememberNavController())
}
