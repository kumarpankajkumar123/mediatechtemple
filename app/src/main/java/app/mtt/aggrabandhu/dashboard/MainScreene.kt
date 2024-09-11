package app.mtt.aggrabandhu.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.dashboard.pages.DonationsPage
import app.mtt.aggrabandhu.dashboard.pages.HomePage
import app.mtt.aggrabandhu.dashboard.pages.ProfilePage
import app.mtt.aggrabandhu.dashboard.pages.RulesRegulationsPage
import app.mtt.aggrabandhu.utils.CircularImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController : NavController) {

    // Create a state for managing the drawer's open/close state
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemClick = {
                    // Handle navigation item click
                    scope.launch { drawerState.close() }
                    // You can navigate here if needed
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Drawer"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                            },
                            icon = {
                                BadgedBox(badge = {
                                    if (navItem.badgeAmount != null)
                                        Badge {
                                            Text(text = navItem.badgeAmount.toString())
                                        }
                                }) {
                                    Icon(
                                        imageVector = navItem.selectedIcon,
                                        contentDescription = "Icon"
                                    )
                                }
                            },
                            label = {
                                Text(text = navItem.title)
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex : Int) {
    when(selectedIndex){
        0-> HomePage()
        1-> DonationsPage()
        2-> RulesRegulationsPage()
        3-> ProfilePage()
    }
}

@Composable
fun DrawerContent(onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(0.7f)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        CircularImage(size = 160.dp, painter = painterResource(id = R.drawable.png_logo))
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .clickable { onItemClick() }
                .padding(start = 16.dp),
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
        Spacer(modifier = Modifier.height(10.dp))
        // Example items in the drawer
        SideNavItem(text = "My Donations", imageVector = Icons.Default.AccountBalanceWallet)
        SideNavItem(text = "Donors List", imageVector = Icons.Default.ListAlt)
        SideNavItem(text = "People Receiving Donations", imageVector = Icons.Default.PeopleAlt)
        SideNavItem(text = "Rules & Regulations", imageVector = Icons.Default.Rule)
    }
}

@Composable
fun SideNavItem(text: String, imageVector: ImageVector) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.White)
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Nothing",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                modifier = Modifier
                    .clickable { },
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun Preview() {
    SideNavItem(text = "Hello", imageVector = Icons.Default.Visibility)
}