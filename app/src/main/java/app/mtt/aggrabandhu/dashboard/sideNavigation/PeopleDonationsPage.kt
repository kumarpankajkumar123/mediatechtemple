package app.mtt.aggrabandhu.dashboard.sideNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.mtt.aggrabandhu.utils.CircularImage
import app.mtt.aggrabandhu.utils.CustomButton

data class DonationsData (
    val name : String,
    val amount : String,
    val date : String,
    val memberID : String,
    val address : String,
    val profileImage : Int ?= null
)

@Preview
@Composable
fun PeopleDonationsPage(navController: NavController ?= null) {

    val mList = arrayListOf(
        (DonationsData("RAMU","2000","19/39/90", "1234","Jaipur, Rajasthan")),
        (DonationsData("Shyam","2100","19/39/90", "1234","Jaipur, Rajasthan")),
        (DonationsData("Suresh","3000","19/39/90", "1234","Jaipur, Rajasthan"))
    )

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
                text = "People Receiving Donations",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        LazyColumn(content = {
            items(mList){
                DonationsDistributed(donationsData = it){
                    navController?.navigate("donors_page")
                }
            }
        })
    }
}

@Composable
private fun DonationsDistributed (
    donationsData: DonationsData,
    onClick : () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    size = 54.dp,
                    painter = if (donationsData.profileImage != null) {
                        painterResource(id = donationsData.profileImage)
                    } else rememberVectorPainter(
                        Icons.Default.Person
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .padding(start = 5.dp)
                ) {
                    Text(
                        text = donationsData.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                    Text(
                        text = "Member ID : ${donationsData.memberID}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                    Text(
                        text = donationsData.address,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Column {
                        Row(modifier = Modifier) {
                            Icon(
                                imageVector = Icons.Default.CurrencyRupee,
                                contentDescription = "Rupee",
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = donationsData.amount,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                            )
                        }
                        Text(
                            text = donationsData.date,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Black,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                CustomButton(
                    text = "View Contributors",
                    background = Color.Black
                ) {
                    onClick.invoke()
                }
            }
        }
    }
}