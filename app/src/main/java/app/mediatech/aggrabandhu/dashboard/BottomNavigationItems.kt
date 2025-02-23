package app.mediatech.aggrabandhu.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Rule
import androidx.compose.material.icons.automirrored.outlined.Rule
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.CoPresent
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Rule
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.ui.graphics.vector.ImageVector


data class BottomNavigationItems (
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

///List of Navigation Items that will be clicked
val items = listOf(
    BottomNavigationItems(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavigationItems(
        title = "Sahyog",
        selectedIcon = Icons.Filled.CoPresent,
        unselectedIcon = Icons.Outlined.CoPresent,
        badgeAmount = 7
    ),
    BottomNavigationItems(
        title = "Rules",
        selectedIcon = Icons.AutoMirrored.Filled.Rule,
        unselectedIcon = Icons.AutoMirrored.Outlined.Rule
    ),
    BottomNavigationItems(
        title = "Support",
        selectedIcon = Icons.Filled.SupportAgent,
        unselectedIcon = Icons.Outlined.SupportAgent
    ),
    BottomNavigationItems(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)