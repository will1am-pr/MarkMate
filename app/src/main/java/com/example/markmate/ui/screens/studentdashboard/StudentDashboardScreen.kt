package com.example.markmate.ui.screens.studentdashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.markmate.R
import com.example.markmate.navigation.ROUT_REQUESTLEAVE
import com.example.markmate.navigation.ROUT_UPDATEPROFILE
import com.example.markmate.navigation.ROUT_VIEWMYATTENDANCE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("📋 Student Dashboard", fontWeight = FontWeight.SemiBold, color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF522C98)) {
                NavigationBarItem(
                    icon = { Icon(painterResource(id = R.drawable.visibility), contentDescription = "Attendance") },
                    label = { Text("Attendance") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_VIEWMYATTENDANCE) }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(id = R.drawable.request), contentDescription = "Leave") },
                    label = { Text("Leave") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_REQUESTLEAVE) }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(id = R.drawable.update), contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_UPDATEPROFILE) } // replace with actual route
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome to your Dashboard",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val dashboardOptions = listOf(
                DashboardItem("View My Attendance", "Student", ROUT_VIEWMYATTENDANCE, R.drawable.view),
                DashboardItem("Request Leave", "Student", ROUT_REQUESTLEAVE, R.drawable.request),
                DashboardItem("Update Profile", "Student", "profile", R.drawable.update)
            )

            dashboardOptions.forEach { item ->
                val cardColor = if (item.role == "Admin") {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }

                DashboardCard(
                    title = "${item.title} [${item.role}]",
                    icon = item.icon,
                    backgroundColor = cardColor,
                    onClick = {
                        navController.navigate(item.route)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


data class DashboardItem(
    val title: String,
    val role: String,
    val route: String,
    val icon: Int // Use Int to represent drawable resources
)

@Composable
fun DashboardCard(
    title: String,
    icon: Int, // Accept Int to represent drawable resources
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },  // Ensures the card is clickable for navigation
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon), // Use painterResource to load drawable
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp) // Updated icon size
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentDashboardPreview() {
    StudentDashboardScreen(navController = rememberNavController())
}
