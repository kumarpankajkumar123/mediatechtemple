package app.mediatech.aggrabandhu.dashboard.pages.profile.joinedUsers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.AllMembersViewModel
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.DonorsCard
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager

@Preview
@Composable
fun ViewJoinedUsersScreen(navController: NavController?= null) {

    val context = LocalContext.current
    val sp = SharedPrefManager(context)

    val id = sp.getMemberID()

    val allMembersViewModel : AllMembersViewModel = hiltViewModel()
    val allMembers = allMembersViewModel.allMembers.collectAsState()

    allMembersViewModel.getViewJoined(id.toString())

    val membersResponseCode = allMembersViewModel.allMembersResponseCode.collectAsState()

    val showProgress = remember { mutableStateOf(true) }

    if (membersResponseCode.value != 0) {
        showProgress.value = false
    }


    if (showProgress.value) {
        LoadingAlertDialog()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController?.popBackStack() }
                    .size(28.dp)
            )
            Text(
                text = "All Referred Members",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }


        if (allMembers.value.isEmpty()){
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 10.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "No Data",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
        LazyColumn(content = {
            items(allMembers.value) {
                DonorsCard(allMemberData = it, true)
            }
        })
    }

}