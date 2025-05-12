package com.example.markmate.ui.screens.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.markmate.navigation.ROUT_VIEWLEAVEREQUESTS
import com.example.markmate.ui.theme.Blue
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

data class LeaveRequest(
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val date: LocalDate,
    val reason: String,
    var status: String = "Pending"
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewLeaveRequestsScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val leaveRequests = remember {
        mutableStateListOf(
            LeaveRequest(userId = "Alice", date = LocalDate.now().minusDays(2), reason = "Medical appointment"),
            LeaveRequest(userId = "Bob", date = LocalDate.now().minusDays(1), reason = "Family emergency", status = "Approved"),
            LeaveRequest(userId = "Charlie", date = LocalDate.now(), reason = "Personal reasons", status = "Pending"),
            LeaveRequest(userId = "Diana", date = LocalDate.now().plusDays(1), reason = "Conference attendance", status = "Stalled")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("📄 Leave Requests", fontSize = 20.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Blue)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Blue) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Dashboard") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_VIEWLEAVEREQUESTS) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Leave") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("About") },
                    selected = false,
                    onClick = { navController.navigate("about") }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color(0xFFF6F9FC))
        ) {
            Text(
                text = "All Leave Requests",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Blue,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(leaveRequests) { index, request ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("👤 User: ${request.userId}", fontWeight = FontWeight.SemiBold)
                            Text("📅 Date: ${request.date}")
                            Text("📝 Reason: ${request.reason}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Status: ${request.status}",
                                fontWeight = FontWeight.Bold,
                                color = when (request.status) {
                                    "Approved" -> Color(0xFF4CAF50)
                                    "Rejected" -> Color(0xFFF44336)
                                    "Stalled" -> Color(0xFFFF9800)
                                    else -> Color.Gray
                                }
                            )
                            if (request.status == "Pending") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            leaveRequests[index] = request.copy(status = "Approved")
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("✅ ${request.userId}'s request approved.")
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                                    ) {
                                        Text("Approve", color = Color.White)
                                    }

                                    Button(
                                        onClick = {
                                            leaveRequests[index] = request.copy(status = "Rejected")
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("❌ ${request.userId}'s request rejected.")
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                                    ) {
                                        Text("Reject", color = Color.White)
                                    }

                                    Button(
                                        onClick = {
                                            leaveRequests[index] = request.copy(status = "Stalled")
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("⏸️ ${request.userId}'s request stalled.")
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                                    ) {
                                        Text("Stall", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ViewLeaveRequestsScreenPreview() {
    ViewLeaveRequestsScreen(navController = rememberNavController())
}
