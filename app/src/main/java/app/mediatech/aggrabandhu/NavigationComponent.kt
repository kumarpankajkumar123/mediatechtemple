package app.mediatech.aggrabandhu

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.mediatech.aggrabandhu.authentication.login.LoginScreen
import app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding.FirstOnboardingScreen
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.SecondOnboardingScreen
import app.mediatech.aggrabandhu.authentication.signup.SignupScreen
import app.mediatech.aggrabandhu.dashboard.DashboardScreen
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.MakeDonationPage
import app.mediatech.aggrabandhu.dashboard.pages.profile.EditProfileScreen
import app.mediatech.aggrabandhu.dashboard.pages.profile.ProfilePage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.AboutUsPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.DonorsPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations.MyDonationsPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.policy.PrivacyPolicyPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.RulesRegulationsPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.supportPage.SupportPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.TermsAndConditionsPage

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
            "second_on_screen/{referenceID}/{name}/{phone}/{password}/{father}/{mother}/{gender}/{maritalStatus}/{spouse}/{marriageDate}/{marriageYears}/{dob}/{ageYears}/{profileUri}",
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
                navArgument("gender") {
                    type = NavType.StringType
                },
                navArgument("maritalStatus") {
                    type = NavType.StringType
                },
                navArgument("spouse") {
                    type = NavType.StringType
                },
                navArgument("marriageDate") {
                    type = NavType.StringType
                },
                navArgument("marriageYears") {
                    type = NavType.StringType
                },
                navArgument("dob") {
                    type = NavType.StringType
                },
                navArgument("ageYears") {
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
        composable("edit_profile_screen") {
            EditProfileScreen(navController = navController)
        }
        composable("make_donation_screen/{bankName}/{ifsc}/{accountNumb}/{upiId}/{memberId}/{id}", arguments = listOf(
            navArgument("bankName") {
                type = NavType.StringType
            },
            navArgument("ifsc") {
                type = NavType.StringType
            },
            navArgument("accountNumb") {
                type = NavType.StringType
            },
            navArgument("upiId") {
                type = NavType.StringType
            },
            navArgument("memberId") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.StringType
            }
        )){
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
        composable("about_page") {
            AboutUsPage(navController = navController)
        }
    }

}