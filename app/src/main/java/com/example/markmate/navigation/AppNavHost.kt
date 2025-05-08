package com.example.markmate.navigation

import RegisterScreen
import android.annotation.SuppressLint
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
import com.example.markmate.ui.screens.admin.AddStudentScreen
import com.example.markmate.ui.screens.admin.AdminDashboardScreen
import com.example.markmate.ui.screens.admin.ManageClassesScreen
import com.example.markmate.ui.screens.admin.ViewAttendanceScreen
import com.example.markmate.ui.screens.home.HomeScreen
import com.example.markmate.viewmodel.AuthViewModel
import com.example.markmate.ui.screens.contact.ContactScreen
import com.example.markmate.ui.screens.splash.SplashScreen
import com.example.markmate.ui.screens.auth.LoginScreen
import com.example.markmate.ui.screens.dashboard.DashboardScreen
import com.example.markmate.ui.screens.user.RequestLeaveScreen
import com.example.markmate.ui.screens.user.ViewMyAttendanceScreen


@SuppressLint("ComposableDestinationInComposeScope", "ViewModelConstructorInComposable")
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
        composable(ROUT_DASHBOARD) {
            DashboardScreen(navController)
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
            RequestLeaveScreen(navController)
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