package app.mtt.aggrabandhu.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import app.mtt.aggrabandhu.dashboard.pages.DonationsPage
import app.mtt.aggrabandhu.dashboard.pages.HomePage
import app.mtt.aggrabandhu.dashboard.pages.ProfilePage
import app.mtt.aggrabandhu.dashboard.pages.RulesRegulationsPage

@Composable
fun MainScreen(navController : NavController) {

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected =  selectedIndex == index ,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            BadgedBox(badge = {
                                if(navItem.badgeAmount != null)
                                    Badge{
                                        Text(text = navItem.badgeAmount.toString())
                                    }
                            }) {
                                Icon(imageVector = navItem.selectedIcon, contentDescription = "Icon")
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
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
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

