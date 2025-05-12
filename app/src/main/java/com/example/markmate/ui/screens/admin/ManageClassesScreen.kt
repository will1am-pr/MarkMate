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
import com.example.markmate.navigation.ROUT_ADDSTUDENT
import com.example.markmate.navigation.ROUT_MANAGECLASSES
import com.example.markmate.navigation.ROUT_VIEWATTENDANCE
import com.example.markmate.navigation.ROUT_VIEWLEAVEREQUESTS

// Sample course list
val courses = listOf("Full-stack MIT Software Development", "Cybersecurity", "Data Science")

@Composable
fun ManageClassesScreen(navController: NavController) {
    var selectedCourse by remember { mutableStateOf(courses.first()) }
    var classDuration by remember { mutableStateOf(2) }
    var customNote by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF003366)
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Add Student") },
                    label = { Text("Add") },
                    selected = false,
                    onClick = { navController.navigate(ROUT_ADDSTUDENT) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Manage Classes") },
                    label = { Text(ROUT_MANAGECLASSES) },
                    selected = true,
                    onClick = { /* Already here */ }
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
                    selected = false,
                    onClick = { navController.navigate(ROUT_VIEWLEAVEREQUESTS) }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFEFF3F8))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Manage Classes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366)
            )

            DropdownMenuWithLabel(
                label = "Select Course",
                items = courses,
                selectedItem = selectedCourse,
                onItemSelected = { selectedCourse = it }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Duration (hrs):", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Slider(
                    value = classDuration.toFloat(),
                    onValueChange = { classDuration = it.toInt().coerceAtLeast(2) },
                    valueRange = 2f..8f,
                    steps = 6,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("$classDuration hrs", fontWeight = FontWeight.Medium)
            }

            OutlinedTextField(
                value = customNote,
                onValueChange = { customNote = it },
                label = { Text("Add notes or plan for the class") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // Save logic
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0033CC))
            ) {
                Text("Schedule Class", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Scheduled Classes (Preview):", fontWeight = FontWeight.SemiBold)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(courses) { course ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(course, fontWeight = FontWeight.Bold)
                            Text("Duration: $classDuration hrs")
                            if (customNote.isNotBlank()) Text("Note: $customNote")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuWithLabel(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label, fontSize = 16.sp)
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedItem)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                items.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onItemSelected(item)
                        expanded = false
                    }, text = { Text(item) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageClassesScreenPreview() {
    ManageClassesScreen(navController = rememberNavController())
}