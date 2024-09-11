package app.mtt.aggrabandhu

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.mtt.aggrabandhu.authentication.LoginScreen
import app.mtt.aggrabandhu.authentication.signup.SignupScreen
import app.mtt.aggrabandhu.dashboard.MainScreen
import app.mtt.aggrabandhu.dashboard.SideNavigation1

/*sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}*/

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_screen") {
        composable("login_screen") {
            LoginScreen(navController = navController)
        }
        composable("signup_screen") {
            SignupScreen(navController = navController)
        }
        composable("dashboard_screen") {
            MainScreen(navController = navController)
        }
    }

}