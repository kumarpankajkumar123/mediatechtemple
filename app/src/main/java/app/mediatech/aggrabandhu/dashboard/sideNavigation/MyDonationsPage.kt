package app.mediatech.aggrabandhu.dashboard.sideNavigation

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
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.DonorsData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.getDonorsList
import app.mediatech.aggrabandhu.utils.CircularImage

data class MyDonationsData(
    val name: String,
    val address : String,
    val amount : String,
    val img : Int ?= null,
    val imageVector: ImageVector?= Icons.Default.PersonPin
)

@Preview
@Composable
fun MyDonationsPage(navController: NavController?=null) {
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
            items(getDonorsList()) {
                AllDonations(donorsData = it)
            }
        })
    }
}


@Composable
private fun AllDonations (
    donorsData: DonorsData
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
                text = "Asharam Babu",
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
                TextDetails(ques = "Name", ans = "Asharam Babu")
                TextDetails(ques = "Address", ans = "Jodhpur, Rajasthan")
                TextDetails(ques = "Bank", ans = "Dena Bank")
                TextDetails(ques = "Date", ans = "33/33/90")
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
                        painter = if (donorsData.img != null) {
                            painterResource(id = donorsData.img)
                        } else rememberVectorPainter(
                            image = donorsData.imageVector!!
                        )
                    )
                    TextDetails(ques = "Member ID", ans = "1234")
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