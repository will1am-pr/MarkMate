package com.example.markmate.ui.screens.contact

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.markmate.ui.theme.Blue
import com.example.markmate.ui.theme.Goldenbrown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Contact") },
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
            NavigationBar(containerColor = Goldenbrown) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 /* navController.navigate(...) */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Health") },
                    label = { Text("Health") },
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 /* navController.navigate(...) */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 /* navController.navigate(...) */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
                    label = { Text("Info") },
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3 /* navController.navigate(...) */ }
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { Toast.makeText(context, "Initiating call to parent...", Toast.LENGTH_SHORT).show() },
                containerColor = Goldenbrown
            ) {
                Icon(Icons.Default.Call, contentDescription = "Call Parent")
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F9))
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text("🧑‍🎓 Student Information", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Blue)
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Name: John M. Doe")
                Text("• Grade: 7A")
                Text("• Medical Condition: Asthma")
                Spacer(modifier = Modifier.height(16.dp))

                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Text("📞 Parent/Guardian Contact", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Blue)
                Spacer(modifier = Modifier.height(8.dp))
                Text("• Name: Mary Doe")
                Text("• Relationship: Mother")
                Text("• Phone: +254 712 345678")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "Calling Mary Doe...", Toast.LENGTH_SHORT).show()
                        // TODO: Integrate real phone dial intent
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call Parent", color = Color.White)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Text("🚨 Emergency Procedures", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Blue)
                Spacer(modifier = Modifier.height(8.dp))
                Text("In case of emergencies such as injuries or medical attacks, ensure the student receives immediate first aid while the parent/guardian is being contacted. The school's health officer should also be notified.")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
    ContactScreen(navController = rememberNavController())
}
