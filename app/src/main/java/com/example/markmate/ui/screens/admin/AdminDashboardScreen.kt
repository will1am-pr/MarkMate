package com.example.markmate.ui.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.markmate.navigation.ROUT_ADDSTUDENT
import com.example.markmate.navigation.ROUT_MANAGECLASSES
import com.example.markmate.navigation.ROUT_REQUESTLEAVE
import com.example.markmate.navigation.ROUT_VIEWATTENDANCE
import com.example.markmate.navigation.ROUT_VIEWLEAVEREQUESTS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "📋 MarkMate Admin",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Add Student") },
                    label = { Text("Add") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_ADDSTUDENT) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Manage Classes") },
                    label = { Text("Classes") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_MANAGECLASSES) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Attendance") },
                    label = { Text("Attendance") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_VIEWATTENDANCE) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Leave") },
                    label = { Text("Leave") },
                    selected = true,
                    onClick = { navController.navigate(ROUT_VIEWLEAVEREQUESTS) }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Welcome back, Admin 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            AdminDashboardCard(
                title = "➕ Add Student",
                icon = Icons.Default.Person,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                navController.navigate(ROUT_ADDSTUDENT)
            }

            AdminDashboardCard(
                title = "📚 Manage Classes",
                icon = Icons.Default.Info,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                navController.navigate(ROUT_MANAGECLASSES)
            }

            AdminDashboardCard(
                title = "📈 View Attendance",
                icon = Icons.Default.List,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                navController.navigate(ROUT_VIEWATTENDANCE)
            }

            AdminDashboardCard(
                title = "📈 View Leave Requests",
                icon = Icons.Default.List,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                navController.navigate(ROUT_VIEWLEAVEREQUESTS)
            }
        }
    }
}

@Composable
fun AdminDashboardCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    AdminDashboardScreen(navController = rememberNavController())
}
