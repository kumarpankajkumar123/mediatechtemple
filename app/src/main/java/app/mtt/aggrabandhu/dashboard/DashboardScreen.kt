package app.mtt.aggrabandhu.dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.SupportAgent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.R
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.DonationsPage
import app.mtt.aggrabandhu.dashboard.pages.HomePage
import app.mtt.aggrabandhu.dashboard.pages.profile.ProfilePage
import app.mtt.aggrabandhu.dashboard.pages.RulesRegulationsPage
import app.mtt.aggrabandhu.utils.CircularImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController : NavController ?= null) {

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
                onItemClick = { route ->
                    // Handle navigation item click
                    scope.launch { drawerState.close() }
                    // You can navigate here if needed
                    navController?.navigate(route)
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Row {
                    TopAppBar(
                        title = {
                            // Username and Circular Image on the right
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Suresh",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                CircularImage(
                                    size = 40.dp,
                                    painter = painterResource(id = R.drawable.png_logo)
                                )
                            }
                        },
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
                }
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
                                Text(
                                    text = navItem.title,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            ContentScreen(navController!!, modifier = Modifier.padding(innerPadding), selectedIndex)
        }
    }
}

@Composable
fun ContentScreen(navController: NavController, modifier: Modifier = Modifier, selectedIndex : Int) {
    when(selectedIndex){
        0-> HomePage()
        1-> DonationsPage(navController)
        2-> RulesRegulationsPage()
        3-> {
            openSupport(LocalContext.current)
            ProfilePage()
        }
        4-> ProfilePage()
    }
}

@Composable
fun DrawerContent(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(0.75f)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        CircularImage(size = 160.dp, painter = painterResource(id = R.drawable.png_logo))
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
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
        //Spacer(modifier = Modifier.height(10.dp))
        // Example items in the drawer
        SideNavItem(text = "My Donations", imageVector = Icons.Default.AccountBalanceWallet){onItemClick.invoke("my_donations_page")}
        SideNavItem(text = "Donors List", imageVector = Icons.Default.ListAlt){onItemClick.invoke("donors_page")}
        SideNavItem(text = "People Receiving Donations", imageVector = Icons.Default.PeopleAlt){onItemClick.invoke("received_donations_page")}
        SideNavItem(text = "Support", imageVector = Icons.Default.SupportAgent){}  //onItemClick.invoke("support_page")
        SideNavItem(text = "Follow us -", imageVector = Icons.Default.AccountCircle){}
        SubNavItem(text = "Facebook") {}
        SubNavItem(text = "Instagram") {}
        SideNavItem(text = "Rules & Regulations", imageVector = Icons.Default.Rule){onItemClick.invoke("rules_page")}
        SideNavItem(text = "Privacy & Policy", imageVector = Icons.Default.Policy){onItemClick.invoke("privacy_policy_page")}
        SideNavItem(text = "Terms & Conditions", imageVector = Icons.Default.PrivacyTip){onItemClick.invoke("terms_page")}
    }
}

@Composable
fun SideNavItem(text: String, imageVector: ImageVector, clickable : ()-> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(Color.White)
                .padding(start = 10.dp)
                .clickable { clickable.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Nothing",
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        HorizontalDivider()
    }
}

@Composable
fun SubNavItem(text: String, clickable : ()-> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color.White)
                .padding(start = 10.dp)
                .clickable { clickable.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(55.dp))
//            Icon(imageVector = Icons.Default.List, contentDescription = )
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun Preview() {
    DashboardScreen(navController = null)
    // SideNavItem(text = "Hello", imageVector = Icons.Default.Visibility)
}

private fun openSupport(context:Context){
    val url = "https://api.whatsapp.com/send?phone=+917597435543"
    val i = Intent(Intent.ACTION_VIEW)
    i.setData(Uri.parse(url))
    context.startActivity(i)
}