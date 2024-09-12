package app.mtt.aggrabandhu

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.mtt.aggrabandhu.authentication.LoginScreen
import app.mtt.aggrabandhu.authentication.signup.SignupScreen
import app.mtt.aggrabandhu.dashboard.DashboardScreen
import app.mtt.aggrabandhu.dashboard.sideNavigation.DonorsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.MyDonationsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.PeopleDonationsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.PrivacyPolicyPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.RulesRegulationsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.SupportPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.TermsAndConditionsPage

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
            DashboardScreen(navController = navController)
        }

        /* ---------- Side Navigation Page ---------------- */
        composable("my_donations_page") {
            MyDonationsPage(navController = navController)
        }
        composable("donors_page") {
            DonorsPage(navController = navController)
        }
        composable("people_donations_page") {
            PeopleDonationsPage(navController = navController)
        }
        composable("support_page") {
            SupportPage(navController = navController)
        }
        composable("rules_page") {
            RulesRegulationsPage(navController = navController)
        }
        composable("privacy_policy_page") {
            PrivacyPolicyPage(navController = navController)
        }
        composable("terms_page") {
            TermsAndConditionsPage(navController = navController)
        }
    }

}