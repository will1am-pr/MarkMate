package com.example.markmate.ui.screens.user

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

data class LeaveRequest(
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val date: LocalDate,
    val reason: String,
    var status: String = "Pending"
)

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestLeaveScreen(
    navController: NavController,
    Admin: Boolean = false,
    currentUserId: String = ""
) {
    val today = LocalDate.now()
    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(1) }

    // Shared list of leave requests (mocked as in-memory store)
    val leaveRequests = remember { mutableStateListOf<LeaveRequest>() }

    var leaveReason by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (Admin) "Admin Panel" else "Request Leave") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Blue) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Leave") },
                    label = { Text("Leave") },
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 }
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEFF3F8))
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (Admin) {
                    Text("📋 All Leave Requests", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    if (leaveRequests.isEmpty()) {
                        Text("No leave requests submitted yet.", color = Color.Gray)
                    } else {
                        LazyColumn {
                            itemsIndexed(leaveRequests) { index, request ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("👤 User: ${request.userId}")
                                        Text("📅 Date: ${request.date}")
                                        Text("📝 Reason: ${request.reason}")
                                        Text("⏳ Status: ${request.status}", fontWeight = FontWeight.Bold)

                                        if (request.status == "Pending") {
                                            Row {
                                                Button(
                                                    onClick = { leaveRequests[index].status = "Approved" },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                                    modifier = Modifier.padding(end = 8.dp)
                                                ) {
                                                    Text("Approve", color = Color.White)
                                                }
                                                Button(
                                                    onClick = { leaveRequests[index].status = "Rejected" },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                                                ) {
                                                    Text("Reject", color = Color.White)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Student/User View
                    val dayOfWeek = today.dayOfWeek
                    Text(
                        text = "Date: $today (${dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }})",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = leaveReason,
                        onValueChange = { leaveReason = it },
                        label = { Text("Reason for leave") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val newRequest = LeaveRequest(
                                userId = currentUserId,
                                date = today,
                                reason = leaveReason
                            )
                            leaveRequests.add(0, newRequest)
                            leaveReason = ""
                            Toast.makeText(context, "Leave request submitted!", Toast.LENGTH_SHORT).show()
                        },
                        enabled = leaveReason.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit Request", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))


                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Text("📜 Your Leave Requests", fontWeight = FontWeight.SemiBold)

                    val userRequests = leaveRequests.filter { it.userId == currentUserId }

                    if (userRequests.isEmpty()) {
                        Text("No leave requests found.", color = Color.Gray)
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
                            items(userRequests) { request ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("📅 ${request.date} - ${request.reason}")
                                        Text("Status: ${request.status}", fontWeight = FontWeight.Bold)
                                    }
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
fun RequestLeaveScreenUserPreview() {
    RequestLeaveScreen(navController = rememberNavController(), Admin = false)
}

@Preview(showBackground = true)
@Composable
fun RequestLeaveScreenAdminPreview() {
    RequestLeaveScreen(navController = rememberNavController(), Admin = true)
}
