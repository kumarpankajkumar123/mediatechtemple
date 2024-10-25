package app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.convertDateFormat
import app.mediatech.aggrabandhu.di.baseUrl
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.TextFieldWithIcons
import coil.compose.rememberAsyncImagePainter

@Preview
@Composable
fun DonorsPage(navController: NavController ?= null) {

    val allMembersViewModel : AllMembersViewModel = hiltViewModel()
    allMembersViewModel.initMembers()
    val allMembers = allMembersViewModel.allMembers.collectAsState()
    var searchTxt by remember { mutableStateOf("") }
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
                text = "All Members",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                TextFieldWithIcons(
                label = "Search...",
                placeholder = "Search",
                maxLength = 30,
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Search,
                value = searchTxt,
                isRequired = false
            ) {
                searchTxt = it
            }
        }
        val filteredList = allMembers.value.filter {
            it.name.contains(searchTxt, ignoreCase = true)
        }

        if (filteredList.isEmpty()){
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
            items(filteredList) {
                DonorsCard(allMemberData = it)
            }
        })
    }
}

@Composable
fun DonorsCard (
    allMemberData: AllMemberData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularImage(
                size = 54.dp,
                painter = rememberAsyncImagePainter(baseUrl+allMemberData.profileUrl)
            )
            Column(modifier = Modifier
                .fillMaxWidth(.58f)
                .padding(start = 5.dp)) {
                Text(
                    text = allMemberData.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
                Text(
                    text = allMemberData.profession,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
                Text(
                    text = "${allMemberData.district},${allMemberData.state}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column {
                    Text(
                        text = "Member ID : ${allMemberData.id}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                    Text(
                        text = "${
                            convertDateFormat(
                                allMemberData.createdAt
                            )
                        }",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Blue,
                    )
                }
            }
        }
    }
}