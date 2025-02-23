package app.mediatech.aggrabandhu.dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.filled.Rule
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Policy
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.R
import app.mediatech.aggrabandhu.dashboard.pages.home.HomePage
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.DonationsPage
import app.mediatech.aggrabandhu.dashboard.pages.profile.ProfilePage
import app.mediatech.aggrabandhu.dashboard.pages.rules.RulesRegulationsPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.supportPage.FB
import app.mediatech.aggrabandhu.dashboard.sideNavigation.supportPage.IG
import app.mediatech.aggrabandhu.dashboard.sideNavigation.supportPage.SupportPage
import app.mediatech.aggrabandhu.dashboard.sideNavigation.supportPage.intentToWeb
import app.mediatech.aggrabandhu.di.baseUrl
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.LogoutDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import app.mediatech.aggrabandhu.viewmodel.LiveDonationsViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController : NavController ?= null) {

    val context = LocalContext.current

    val liveDonationsViewModel : LiveDonationsViewModel = hiltViewModel()
    val size = liveDonationsViewModel.liveDonationsData.collectAsState()

    val sharedPref = SharedPrefManager(context)
    var name by remember { mutableStateOf("") }
    var profile by remember { mutableStateOf("") }

    name = sharedPref.getFullName().toString()
    profile = sharedPref.getProfileUrl().toString()

    val logoutDialog = remember { mutableStateOf(false) }

    // Create a state for managing the drawer's open/close state
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    LogoutDialog(
        showDialog = logoutDialog.value,
        onConfirm = {
            sharedPref.logOut()
            logoutDialog.value = false
            navController?.navigate("login_screen") {

            }
        }
    ) {
        logoutDialog.value = false
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(sharedPref,
                onItemClick = { route ->
                    // Handle navigation item click
                    scope.launch { drawerState.close() }
                    // You can navigate here if needed
                    if (route == "login_screen") {
                        if (sharedPref.getLoginStatus()) {
                            logoutDialog.value = true
                        } else {
                            navController?.navigate(route)
                        }
                    } else {
                        navController?.navigate(route)
                    }
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
                                    text = name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(end = 6.dp)
                                )
                                CircularImage(
                                    size = 30.dp,
                                    painter = rememberAsyncImagePainter(model = if (sharedPref.getLoginStatus()){"$baseUrl$profile"} else {profile})
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
                                            Text(text = size.value.size.toString())
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
            ContentScreen(navController!!, liveDonationsViewModel, modifier = Modifier.padding(innerPadding), selectedIndex)
        }
    }
}

@Composable
fun ContentScreen(navController: NavController, liveDonationsViewModel: LiveDonationsViewModel, modifier: Modifier = Modifier, selectedIndex : Int) {
    Column( modifier = modifier ) {
        when(selectedIndex){
            0-> HomePage()
            1-> DonationsPage(navController, liveDonationsViewModel)
            2-> RulesRegulationsPage()
            3-> SupportPage(fromDashboard = true)
            4-> ProfilePage(navController)
        }
    }
}

@Composable
fun DrawerContent(sharedPrefManager: SharedPrefManager, onItemClick: (String) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        CircularImage(size = 140.dp, painter = painterResource (id = R.drawable.png_logo) )
        Text (
            text = stringResource(id = R.string.app_name),
            modifier = Modifier,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
        // Spacer(modifier = Modifier.height(10.dp))
        // Example items in the drawer
        if (sharedPrefManager.getLoginStatus()) {
            SideNavItem(text = "My Referrals", imageVector = Icons.AutoMirrored.Filled.ListAlt){onItemClick.invoke("joined_users_page")}
            SideNavItem(
                text = "My Donations",
                imageVector = Icons.Default.AccountBalanceWallet
            ) { onItemClick.invoke("my_donations_page") }
        }
        SideNavItem(text = "Members List", imageVector = Icons.AutoMirrored.Filled.ListAlt){onItemClick.invoke("donors_page")}
        SideNavItem(text = "Sahyog List", imageVector = Icons.Default.PeopleAlt){onItemClick.invoke("received_donations_page")}
        SideNavItem(text = "Support", imageVector = Icons.Default.SupportAgent){onItemClick.invoke("support_page")}  //
        SideNavItem(text = "Follow us -", imageVector = Icons.Default.AccountCircle){}
        SubNavItem(imageVector = R.drawable.png_fb, text = "Facebook") { intentToWeb(FB, context) }
        SubNavItem(imageVector = R.drawable.png_ig, text = "Instagram") { intentToWeb(IG, context) }
        SideNavItem(text = "Rules & Regulations", imageVector = Icons.AutoMirrored.Filled.Rule){onItemClick.invoke("rules_page")}
        SideNavItem(text = "Privacy & Policy", imageVector = Icons.Default.Policy){onItemClick.invoke("privacy_policy_page")}
        SideNavItem(text = "About Us", imageVector = Icons.Default.Policy){onItemClick.invoke("about_page")}
//        SideNavItem(text = "Terms & Conditions", imageVector = Icons.Default.PrivacyTip){onItemClick.invoke("terms_page")}

        Spacer(modifier = Modifier.height(20.dp))
        if (sharedPrefManager.getLoginStatus()) {
            LogOut("Log out") {
                onItemClick.invoke("login_screen")
            }
        } else {
            LogOut("Log in") {
                onItemClick.invoke("login_screen")
            }
            Spacer(modifier = Modifier.height(10.dp))
            LogOut("Sign up") {
                onItemClick.invoke("signup_screen")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = try {
                "Version : ${context.packageManager.getPackageInfo(context.packageName,0).versionName}"}
            catch (e:Exception) {
                "Version : Not found"
            },
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(20.dp))
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
fun SubNavItem(
    imageVector: Int,
    text: String,
    clickable : ()-> Unit) {
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
            Spacer(modifier = Modifier.width(40.dp))
            Image(
                painter = painterResource(id = imageVector),
                contentDescription = "Nothing",
                modifier = Modifier.size(24.dp)
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

@Preview
@Composable
private fun Preview() {
    DashboardScreen(navController = null)
    // SideNavItem(text = "Hello", imageVector = Icons.Default.Visibility)
}

fun openSupport(context:Context, number : String){
    val url = "https://api.whatsapp.com/send?phone=+91$number"
    val i = Intent(Intent.ACTION_VIEW)
    i.setData(Uri.parse(url))
    context.startActivity(i)
}

@Composable
fun LogOut(
    text : String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(CornerSize(size = 10.dp)),
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(colorResource(id = R.color.orange))
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.orange)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                maxLines = 1,
                fontSize = 18.sp,
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .background(colorResource(id = R.color.orange))
                    .padding(5.dp, 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}