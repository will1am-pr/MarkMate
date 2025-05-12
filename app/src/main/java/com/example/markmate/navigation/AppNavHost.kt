package com.example.markmate.navigation

import RegisterScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.markmate.data.UserDatabase
import com.example.markmate.repository.UserRepository
import com.example.markmate.ui.screens.about.AboutScreen
import com.example.markmate.ui.screens.about.LeaveSystemScreen
import com.example.markmate.ui.screens.admin.AddStudentScreen
import com.example.markmate.ui.screens.admin.AdminDashboardScreen
import com.example.markmate.ui.screens.admin.ManageClassesScreen
import com.example.markmate.ui.screens.admin.ViewAttendanceScreen
import com.example.markmate.ui.screens.admin.ViewLeaveRequestsScreen
import com.example.markmate.ui.screens.home.HomeScreen
import com.example.markmate.viewmodel.AuthViewModel
import com.example.markmate.ui.screens.contact.ContactScreen
import com.example.markmate.ui.screens.splash.SplashScreen
import com.example.markmate.ui.screens.auth.LoginScreen
import com.example.markmate.ui.screens.studentdashboard.StudentDashboardScreen
import com.example.markmate.ui.screens.user.UpdateProfileScreen
import com.example.markmate.ui.screens.user.ViewMyAttendanceScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }
        composable(ROUT_CONTACT) {
            ContactScreen(navController)
        }
        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUT_STUDENTDASHBOARD) {
            StudentDashboardScreen(navController)
        }
        composable(ROUT_ADMINDASHBOARD) {
            AdminDashboardScreen(navController)
        }
        composable(ROUT_ADDSTUDENT) {
            AddStudentScreen(navController)
        }
        composable(ROUT_MANAGECLASSES) {
            ManageClassesScreen(navController)
        }
        composable(ROUT_VIEWATTENDANCE) {
            ViewAttendanceScreen(navController)
        }
        composable(ROUT_VIEWMYATTENDANCE) {
            ViewMyAttendanceScreen(navController)
        }
        composable(ROUT_REQUESTLEAVE) {
            LeaveSystemScreen(navController)
        }
        composable(ROUT_VIEWLEAVEREQUESTS) {
            ViewLeaveRequestsScreen(navController)
        }
        composable(ROUT_UPDATEPROFILE) {
            UpdateProfileScreen(navController)
        }



        //AUTHENTICATION

        // Initialize Room Database and Repository for Authentication
        val appDatabase = UserDatabase.getDatabase(context)
        val authRepository = UserRepository(appDatabase.userDao())
        val authViewModel: AuthViewModel = AuthViewModel(authRepository)
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }
    }
}