package com.example.markmate.ui.screens.admin

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

// -------------------- DATA MODEL --------------------
@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val course: String,
    val classDate: String
)

// -------------------- DAO --------------------
@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)
}

// -------------------- DATABASE --------------------
@Database(entities = [Student::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_database"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}

// -------------------- UI --------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val dao = db.studentDao()

    var fullName by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var course by remember { mutableStateOf("") }
    var classdate by remember { mutableStateOf("") }

    var mExpanded by remember { mutableStateOf(false) }
    val options = listOf("MIT", "Cybersecurity", "Data Science")
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    var selectedIndex by remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register Student", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF003366))
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF003366)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Home") },
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Info") },
                    label = { Text("Info") },
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3 }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add action */ },
                containerColor = Color(0xFF003366)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .systemBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text("Fill in Student Information", style = MaterialTheme.typography.headlineSmall)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Column {
                        OutlinedTextField(
                            value = course,
                            onValueChange = { course = it },
                            label = { Text("Select Course") },
                            trailingIcon = {
                                Icon(
                                    icon,
                                    "Dropdown",
                                    Modifier.clickable { mExpanded = !mExpanded }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    mTextFieldSize = coordinates.size.toSize()
                                },
                            shape = RoundedCornerShape(10.dp)
                        )

                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                        ) {
                            options.forEach { label ->
                                DropdownMenuItem(
                                    text = { Text(text = label) },
                                    onClick = {
                                        course = label
                                        mExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Text("Select Class Date & Time", style = MaterialTheme.typography.bodyLarge)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val calendar = Calendar.getInstance()

                        Button(
                            onClick = {
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)

                                DatePickerDialog(
                                    context,
                                    { _, selectedYear, selectedMonth, selectedDay ->
                                        val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                                        val minute = calendar.get(Calendar.MINUTE)

                                        android.app.TimePickerDialog(
                                            context,
                                            { _, selectedHour, selectedMinute ->
                                                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                                                classdate = "$selectedDate $selectedTime"
                                            },
                                            hour,
                                            minute,
                                            true
                                        ).show()
                                    },
                                    year,
                                    month,
                                    day
                                ).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                        ) {
                            Text("Pick Date & Time", color = Color.White)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        OutlinedTextField(
                            value = classdate,
                            onValueChange = {},
                            label = { Text("Selected") },
                            readOnly = true,
                            modifier = Modifier.weight(1f),
                            trailingIcon = { Text("📅") },
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (fullName.text.isBlank() || email.text.isBlank() || course.isBlank() || classdate.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please fill in all fields.")
                        }
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            dao.insert(
                                Student(
                                    fullName = fullName.text,
                                    email = email.text,
                                    course = course,
                                    classDate = classdate
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0033CC))
            ) {
                Text("Save Student", color = Color.White, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddStudentScreenPreview() {
    AddStudentScreen(navController = rememberNavController())
}
