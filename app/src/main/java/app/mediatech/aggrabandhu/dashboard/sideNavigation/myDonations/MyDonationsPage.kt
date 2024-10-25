package app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.convertDateFormat
import app.mediatech.aggrabandhu.di.baseUrl
import app.mediatech.aggrabandhu.utils.CircularImage
import app.mediatech.aggrabandhu.utils.LoadingAlertDialog
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import coil.compose.rememberAsyncImagePainter

@Preview
@Composable
fun MyDonationsPage(navController: NavController?=null) {

    val context = LocalContext.current
    val myDonationViewmodel : MyDonationViewmodel = hiltViewModel()

    val showProgress = remember { mutableStateOf(false) }

    val code = myDonationViewmodel.responseCode.collectAsState()
    val data = myDonationViewmodel.responseData.collectAsState()

    val sp = SharedPrefManager(context)

    if (!myDonationViewmodel.isGot) {
        myDonationViewmodel.isGot = true
        myDonationViewmodel.myDonation(sp.getMemberID().toString(), context)
    }

    if (code.value != 0) {
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
                text = "My Donations",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        LazyColumn(content = {
            items(data.value) {
                AllDonations(donorsData = it)
            }
        })
    }
}


@Composable
private fun AllDonations (
    donorsData: MyDonation
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
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Donated to",
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 5.dp),
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = donorsData.to.name,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 2.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

        }

        Row() {
            Column(modifier = Modifier
                .fillMaxWidth(0.6f)
            ) {
                TextDetails(ques = "Name", ans = donorsData.to.name)
                TextDetails(ques = "Address", ans = "${donorsData.to.district},${donorsData.to.state}")
                TextDetails(ques = "Payment Method", ans = donorsData.payment_method)
                TextDetails(ques = "Date", ans = convertDateFormat(donorsData.donation_date).toString())
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularImage(
                        size = 54.dp,
                        painter = rememberAsyncImagePainter(model = "$baseUrl${donorsData.to.profileUrl}")
                    )
                    TextDetails(ques = "Member ID", ans = donorsData.to.id.toString())
                }
            }
        }

    }
}

@Composable
fun TextDetails(
    modifier: Modifier = Modifier,
    ques : String,
    ans : String,
) {
    Row(
        modifier = modifier
            .padding(start = 10.dp, top = 5.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$ques : ",
            fontSize = 14.sp,
            modifier = Modifier,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Text(
            text = ans,
            fontSize = 14.sp,
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}
