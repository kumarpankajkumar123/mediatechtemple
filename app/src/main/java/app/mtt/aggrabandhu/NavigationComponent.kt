package app.mtt.aggrabandhu

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.mtt.aggrabandhu.authentication.login.LoginScreen
import app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding.FirstOnboardingScreen
import app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding.SecondOnboardingScreen
import app.mtt.aggrabandhu.authentication.signup.SignupScreen
import app.mtt.aggrabandhu.dashboard.DashboardScreen
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.MakeDonationPage
import app.mtt.aggrabandhu.dashboard.pages.profile.EditProfileScreen
import app.mtt.aggrabandhu.dashboard.pages.profile.ProfilePage
import app.mtt.aggrabandhu.dashboard.pages.profile.ProfileViewModel
import app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers.DonorsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.MyDonationsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.PrivacyPolicyPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.RulesRegulationsPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.SupportPage
import app.mtt.aggrabandhu.dashboard.sideNavigation.TermsAndConditionsPage

@Composable
fun NavigationComponent(loginStatus : Boolean) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (loginStatus) "dashboard_screen" else "login_screen"
    ) {
        composable("login_screen") {
            LoginScreen(navController = navController)
        }
        composable("signup_screen") {
            SignupScreen(navController = navController)
        }
        composable("first_on_screen/{referenceID}/{name}/{phone}/{password}",
            arguments = listOf(
                navArgument("referenceID") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("phone") {
                    type = NavType.StringType
                },
                navArgument("password") {
                    type = NavType.StringType
                }
            )) {
            FirstOnboardingScreen(navController = navController)
        }
        composable(
            "second_on_screen/{referenceID}/{name}/{phone}/{password}/{father}/{mother}/{gotra}/{maritalStatus}/{spouse}/{dob}/{profession}/{profileUri}",
            arguments = listOf(
                navArgument("referenceID") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("phone") {
                    type = NavType.StringType
                },
                navArgument("password") {
                    type = NavType.StringType
                },
                navArgument("father") {
                    type = NavType.StringType
                },
                navArgument("mother") {
                    type = NavType.StringType
                },
                navArgument("gotra") {
                    type = NavType.StringType
                },
                navArgument("maritalStatus") {
                    type = NavType.StringType
                },
                navArgument("spouse") {
                    type = NavType.StringType
                },
                navArgument("dob") {
                    type = NavType.StringType
                },
                navArgument("profession") {
                    type = NavType.StringType
                },
                navArgument("profileUri") {
                    type = NavType.StringType
                }
        )) {
            SecondOnboardingScreen(navController = navController)
        }

        composable("dashboard_screen") {
            DashboardScreen(navController = navController)
        }
        composable("profile_screen") {
            ProfilePage(navController = navController)
        }
        composable("edit_profile_screen/{name}/{phone}/{father}/{mother}/{gotra}/{maritalStatus}/{spouse}/{dob}/{profession}/{profileUri}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("phone") {
                    type = NavType.StringType
                },
                navArgument("father") {
                    type = NavType.StringType
                },
                navArgument("mother") {
                    type = NavType.StringType
                },
                navArgument("gotra") {
                    type = NavType.StringType
                },
                navArgument("maritalStatus") {
                    type = NavType.StringType
                },
                navArgument("spouse") {
                    type = NavType.StringType
                },
                navArgument("dob") {
                    type = NavType.StringType
                },
                navArgument("profession") {
                    type = NavType.StringType
                },
                navArgument("profileUri") {
                    type = NavType.StringType
                }
            )) {
            EditProfileScreen(navController = navController)
        }
        composable("make_donation_screen") {
            MakeDonationPage(navController = navController)
        }

        /* ---------- Side Navigation Page ---------------- */
        composable("my_donations_page") {
            MyDonationsPage(navController = navController)
        }
        composable("donors_page") {
            DonorsPage(navController = navController)
        }
        composable("received_donations_page") {
            ReceivedDonationsPage(navController = navController)
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